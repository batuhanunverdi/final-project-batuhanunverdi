package org.patikadev.finalprojectbatuhanunverdi.service.implementation;

import lombok.RequiredArgsConstructor;
import org.patikadev.finalprojectbatuhanunverdi.entity.Account;
import org.patikadev.finalprojectbatuhanunverdi.entity.Card;
import org.patikadev.finalprojectbatuhanunverdi.entity.TransferLog;
import org.patikadev.finalprojectbatuhanunverdi.entity.enums.AccountCurrency;
import org.patikadev.finalprojectbatuhanunverdi.entity.enums.CardType;
import org.patikadev.finalprojectbatuhanunverdi.exception.BusinessServiceOperationException;
import org.patikadev.finalprojectbatuhanunverdi.model.ExchangeModel;
import org.patikadev.finalprojectbatuhanunverdi.model.request.CardRequest;
import org.patikadev.finalprojectbatuhanunverdi.model.request.DepositRequest;
import org.patikadev.finalprojectbatuhanunverdi.model.request.PaymentRequest;
import org.patikadev.finalprojectbatuhanunverdi.repository.AccountRepository;
import org.patikadev.finalprojectbatuhanunverdi.repository.CardRepository;
import org.patikadev.finalprojectbatuhanunverdi.repository.TransferLogRepository;
import org.patikadev.finalprojectbatuhanunverdi.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

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
    @Override
    public void createCard(CardRequest cardRequest) {
        Card card = new Card();

        Account account = accountRepository.findById(cardRequest.getAccount()).orElseThrow(()
                        -> new BusinessServiceOperationException.AccountNotFound("Account Not Found"));
        if(cardRepository.findByAccount(account).isPresent()){
            throw new BusinessServiceOperationException.AccountFoundException("This account is already associated with a card.");
        }
        Random random = new Random();
        String randomCardNumber = String.valueOf(random.nextLong(9999999999999999L)).replace(".","");
        card.setAccount(account);
        card.setCardNumber(randomCardNumber);
        card.setCardPassword(cardRequest.getCardPassword());
        int randomCvv = random.nextInt(100,999);
        card.setCvv(randomCvv);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR,6);
        card.setExpirationDate(calendar.getTime());
        card.setCardType(cardRequest.getCardType());
        if(cardRequest.getCardType().equals(CardType.Credit)){
            card.setCardLimit(cardRequest.getCardLimit());
        }
        if(cardRequest.getCardType().equals(CardType.Debit)){
            card.setCardLimit(BigDecimal.ZERO);
        }
        cardRepository.save(card);
    }
    @Override
    public void paymentByCard(PaymentRequest paymentRequest) {
        Card card = cardRepository.findByCardNumber(paymentRequest.getCardNumber()).orElseThrow(()
                -> new BusinessServiceOperationException.CardNotFound("Card Not Found"));
        if(card.getCardPassword()!= paymentRequest.getCardPassword()){
            throw new BusinessServiceOperationException.WrongPasswordException("Wrong Password");
        }
        Account account = accountRepository.findById(card.getAccount().getId()).orElseThrow(()
                -> new BusinessServiceOperationException.AccountNotFound("Account Not Found"));
        ResponseEntity<ExchangeModel> exchangeModel = exchangeService.getExchangeByBaseCurrency
                (paymentRequest.getAccountCurrency().toString());
        if(!exchangeModel.getBody().getBase().equals(AccountCurrency.TRY.toString()) &&
                !exchangeModel.getBody().getBase().equals(AccountCurrency.EUR.toString()) &&
                !exchangeModel.getBody().getBase().equals(AccountCurrency.USD.toString()) ){
            throw new BusinessServiceOperationException.ExchangeNotFound("Exchange type not found");
        }
        BigDecimal newBalance = account.getBalance().subtract(paymentRequest.getPrice().multiply(exchangeModel.getBody().getRates()
                .exchangeValueByReceiverCurrency(account.getAccountCurrency().toString())));
        if(card.getCardType().equals(CardType.Debit)) {
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
            }
            else{
                throw new BusinessServiceOperationException.BalanceException("You dont have enough money to payment");
            }
        } else if (card.getCardType().equals(CardType.Credit)){
            if(account.getBalance().subtract(newBalance).negate().compareTo(card.getCardLimit())>=0){
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
        if(card.getCardPassword()!= depositRequest.getCardPassword()){
            throw new BusinessServiceOperationException.WrongPasswordException("Wrong Password");
        }
        Account account = accountRepository.findById(card.getAccount().getId()).orElseThrow(()
                -> new BusinessServiceOperationException.AccountNotFound("Account Not Found"));


    }

}
