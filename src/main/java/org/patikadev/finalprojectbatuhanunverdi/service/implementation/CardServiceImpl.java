package org.patikadev.finalprojectbatuhanunverdi.service.implementation;

import lombok.RequiredArgsConstructor;
import org.patikadev.finalprojectbatuhanunverdi.converter.CardConverter;
import org.patikadev.finalprojectbatuhanunverdi.entity.Account;
import org.patikadev.finalprojectbatuhanunverdi.entity.Card;
import org.patikadev.finalprojectbatuhanunverdi.entity.TransferLog;
import org.patikadev.finalprojectbatuhanunverdi.entity.enums.AccountCurrency;
import org.patikadev.finalprojectbatuhanunverdi.entity.enums.CardType;
import org.patikadev.finalprojectbatuhanunverdi.exception.BusinessServiceOperationException;
import org.patikadev.finalprojectbatuhanunverdi.model.ExchangeModel;
import org.patikadev.finalprojectbatuhanunverdi.model.request.CardRequest;
import org.patikadev.finalprojectbatuhanunverdi.model.request.DebtPaymentRequest;
import org.patikadev.finalprojectbatuhanunverdi.model.request.DepositRequest;
import org.patikadev.finalprojectbatuhanunverdi.model.request.PaymentRequest;
import org.patikadev.finalprojectbatuhanunverdi.repository.AccountRepository;
import org.patikadev.finalprojectbatuhanunverdi.repository.CardRepository;
import org.patikadev.finalprojectbatuhanunverdi.repository.TransferLogRepository;
import org.patikadev.finalprojectbatuhanunverdi.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * @author Mert Batuhan UNVERDI
 * @since 2.06.2022
 */
@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;
    private final ExchangeServiceImpl exchangeService;
    private final TransferLogRepository transferLogRepository;
    private final CardConverter cardConverter;

    @Override
    public void createCard(CardRequest cardRequest) {
        Card card = cardConverter.toCard(cardRequest);
        if (cardRepository.findByAccount(card.getAccount()).isPresent()) {
            throw new BusinessServiceOperationException.AccountFoundException("This account is already associated with a card.");
        }
        cardRepository.save(card);
    }

    @Override
    public void paymentByCard(PaymentRequest paymentRequest) {
        Card card = cardRepository.findByCardNumber(paymentRequest.getCardNumber()).orElseThrow(()
                -> new BusinessServiceOperationException.CardNotFound("Card Not Found"));
        if (card.getCardPassword() != paymentRequest.getCardPassword()) {
            throw new BusinessServiceOperationException.WrongPasswordException("Wrong Password");
        }
        Account account = accountRepository.findById(card.getAccount().getId()).orElseThrow(()
                -> new BusinessServiceOperationException.AccountNotFound("Account Not Found"));
        ResponseEntity<ExchangeModel> exchangeModel = exchangeService.getExchangeByBaseCurrency
                (paymentRequest.getAccountCurrency().toString());
        if (!Objects.requireNonNull(exchangeModel.getBody()).getBase().equals(AccountCurrency.TRY.toString()) &&
                !exchangeModel.getBody().getBase().equals(AccountCurrency.EUR.toString()) &&
                !exchangeModel.getBody().getBase().equals(AccountCurrency.USD.toString())) {
            throw new BusinessServiceOperationException.ExchangeNotFound("Exchange type not found");
        }
        BigDecimal newBalance = account.getBalance().subtract(paymentRequest.getPrice().multiply(exchangeModel.getBody().getRates()
                .exchangeValueByReceiverCurrency(account.getAccountCurrency().toString())));
        if (card.getCardType().equals(CardType.Debit)) {
            if (account.getBalance().compareTo(newBalance) > 0) {
                account.setBalance(newBalance);
                accountRepository.save(account);
                TransferLog transferLog = new TransferLog();
                transferLog.setSenderIBan(account.getIBAN());
                transferLog.setReceiverIBan(paymentRequest.getReceiverIban());
                transferLog.setReceiverName(paymentRequest.getReceiverName());
                transferLog.setBalance(paymentRequest.getPrice());
                transferLog.setSendingTime(new Date());
                transferLogRepository.save(transferLog);
            } else {
                throw new BusinessServiceOperationException.BalanceException("You dont have enough money to payment");
            }
        } else if (card.getCardType().equals(CardType.Credit)) {
            if (account.getBalance().subtract(newBalance).negate().compareTo(card.getCardLimit()) >= 0) {
                throw new BusinessServiceOperationException.BalanceException("Inadequate limit.");
            }
            account.setBalance(newBalance);
            accountRepository.save(account);
            TransferLog transferLog = new TransferLog();
            transferLog.setSenderIBan(account.getIBAN());
            transferLog.setReceiverIBan(paymentRequest.getReceiverIban());
            transferLog.setReceiverName(paymentRequest.getReceiverName());
            transferLog.setBalance(paymentRequest.getPrice());
            transferLog.setSendingTime(new Date());
            transferLogRepository.save(transferLog);
        }
    }

    @Override
    public void depositMoney(DepositRequest depositRequest) {
        Card card = cardRepository.findByCardNumber(depositRequest.getCardNumber()).orElseThrow(()
                -> new BusinessServiceOperationException.CardNotFound("Card Not Found"));
        if (card.getCardPassword() != depositRequest.getCardPassword()) {
            throw new BusinessServiceOperationException.WrongPasswordException("Wrong Password");
        }
        Account account = accountRepository.findById(card.getAccount().getId()).orElseThrow(()
                -> new BusinessServiceOperationException.AccountNotFound("Account Not Found"));
        ResponseEntity<ExchangeModel> exchangeModel = exchangeService.getExchangeByBaseCurrency
                (depositRequest.getCurrency().toString());
        if (!Objects.requireNonNull(exchangeModel.getBody()).getBase().equals(AccountCurrency.TRY.toString()) &&
                !exchangeModel.getBody().getBase().equals(AccountCurrency.EUR.toString()) &&
                !exchangeModel.getBody().getBase().equals(AccountCurrency.USD.toString())) {
            throw new BusinessServiceOperationException.ExchangeNotFound("Exchange type not found");
        }
        BigDecimal newBalance = account.getBalance().add(depositRequest.getAmount().multiply(exchangeModel.getBody().getRates()
                .exchangeValueByReceiverCurrency(account.getAccountCurrency().toString())));
        account.setBalance(newBalance);
        accountRepository.save(account);
    }

    @Override
    public void debtPaymentByAccount(DebtPaymentRequest debtPaymentRequest) {
        Account senderAccount = accountRepository.findById(debtPaymentRequest.getSenderAccount()).orElseThrow(()
                -> new BusinessServiceOperationException.AccountNotFound("Account Not Found"));

        Account receiverAccount = accountRepository.findById(debtPaymentRequest.getReceiverAccount()).orElseThrow(()
                -> new BusinessServiceOperationException.AccountNotFound("Account Not Found"));

        if (!senderAccount.getUser().equals(receiverAccount.getUser())) {
            throw new BusinessServiceOperationException.AccountTypeException("You can't pay someone's debt");
        }
        if (!senderAccount.getBalance().equals(debtPaymentRequest.getAmount())) {
            throw new BusinessServiceOperationException.AccountTypeException("You do not have enough " +
                    "money in your account to pay the amount of debt you specified.");
        }
        BigDecimal newBalance = senderAccount.getBalance().subtract(debtPaymentRequest.getAmount());
        senderAccount.setBalance(newBalance);
        ResponseEntity<ExchangeModel> exchangeModel = exchangeService.getExchangeByBaseCurrency
                (senderAccount.getAccountCurrency().toString());
        if (!Objects.requireNonNull(exchangeModel.getBody()).getBase().equals(AccountCurrency.TRY.toString()) &&
                !exchangeModel.getBody().getBase().equals(AccountCurrency.EUR.toString()) &&
                !exchangeModel.getBody().getBase().equals(AccountCurrency.USD.toString())) {
            throw new BusinessServiceOperationException.ExchangeNotFound("Exchange type not found");
        }
        newBalance = receiverAccount.getBalance().add(debtPaymentRequest.getAmount().multiply(exchangeModel.getBody().getRates()
                .exchangeValueByReceiverCurrency(receiverAccount.getAccountCurrency().toString())));
        receiverAccount.setBalance(newBalance);
        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);


    }

}
