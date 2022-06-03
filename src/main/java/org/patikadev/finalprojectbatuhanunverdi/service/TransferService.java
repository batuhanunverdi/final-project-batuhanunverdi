package org.patikadev.finalprojectbatuhanunverdi.service;

import org.patikadev.finalprojectbatuhanunverdi.entity.Account;
import org.patikadev.finalprojectbatuhanunverdi.model.request.PaymentRequest;

import java.math.BigDecimal;

/**
 * @author Mert Batuhan UNVERDI
 * @since 21.05.2022
 */
public interface TransferService {

    void moneyTransferControl(String sender, String receiver, BigDecimal balance) throws NoSuchFieldException;

    void moneyTransfer(Account senderAccount, Account receiverAccount, BigDecimal balance) throws NoSuchFieldException;
}
