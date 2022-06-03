package org.patikadev.finalprojectbatuhanunverdi.service.implementation;

import lombok.RequiredArgsConstructor;
import org.patikadev.finalprojectbatuhanunverdi.entity.Account;
import org.patikadev.finalprojectbatuhanunverdi.entity.TransferLog;
import org.patikadev.finalprojectbatuhanunverdi.entity.enums.AccountCurrency;
import org.patikadev.finalprojectbatuhanunverdi.entity.enums.AccountType;
import org.patikadev.finalprojectbatuhanunverdi.exception.BusinessServiceOperationException;
import org.patikadev.finalprojectbatuhanunverdi.model.ExchangeModel;
import org.patikadev.finalprojectbatuhanunverdi.repository.AccountRepository;
import org.patikadev.finalprojectbatuhanunverdi.repository.TransferLogRepository;
import org.patikadev.finalprojectbatuhanunverdi.service.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;


/**
 * @author Mert Batuhan UNVERDI
 * @since 21.05.2022
 */
@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
    private final AccountRepository accountRepository;
    private final TransferLogRepository transferLogRepository;
    private final ExchangeServiceImpl exchangeService;

    @Override
    public void moneyTransferControl(String sender, String receiver, BigDecimal balance) {
        Account senderAccount = accountRepository.findByIBAN(sender).orElseThrow(()
                -> new BusinessServiceOperationException.AccountNotFound("Account not found"));
        Account receiverAccount = accountRepository.findByIBAN(receiver).orElseThrow(()
                -> new BusinessServiceOperationException.AccountNotFound("Account not found"));

        if (senderAccount.getUser().equals(receiverAccount.getUser())) {
            moneyTransfer(senderAccount, receiverAccount, balance);
        } else {
            if (senderAccount.getAccountType().equals(AccountType.Deposit)) {
                throw new BusinessServiceOperationException.AccountTypeException("You can't send money with Deposit account to someone");
            }
            moneyTransfer(senderAccount, receiverAccount, balance);
        }
    }

    public void moneyTransfer(Account senderAccount, Account receiverAccount, BigDecimal balance) {
        if (senderAccount.getBalance().compareTo(balance) >= 0) {
            BigDecimal newBalance = senderAccount.getBalance().subtract(balance);
            senderAccount.setBalance(newBalance);
            ResponseEntity<ExchangeModel> exchangeModel = exchangeService.getExchangeByBaseCurrency
                    (senderAccount.getAccountCurrency().toString());
            if (!Objects.requireNonNull(exchangeModel.getBody()).getBase().equals(AccountCurrency.TRY.toString()) &&
                    !exchangeModel.getBody().getBase().equals(AccountCurrency.EUR.toString()) &&
                    !exchangeModel.getBody().getBase().equals(AccountCurrency.USD.toString())) {
                throw new BusinessServiceOperationException.ExchangeNotFound("Exchange type not found");
            }
            receiverAccount.setBalance(balance.multiply(exchangeModel.getBody().getRates()
                    .exchangeValueByReceiverCurrency(receiverAccount.getAccountCurrency().toString())));
            accountRepository.save(senderAccount);
            accountRepository.save(receiverAccount);
            TransferLog transferLog = new TransferLog();
            transferLog.setSenderIBan(senderAccount.getIBAN());
            transferLog.setReceiverIBan(receiverAccount.getIBAN());
            transferLog.setBalance(balance);
            transferLog.setSendingTime(new Date());
            transferLog.setReceiverName(receiverAccount.getUser().getName());
            transferLogRepository.save(transferLog);
        } else {
            throw new BusinessServiceOperationException.BalanceException("You dont have enough money to send");
        }
    }


}
