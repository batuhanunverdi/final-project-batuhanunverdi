package org.patikadev.finalprojectbatuhanunverdi.service;

import org.patikadev.finalprojectbatuhanunverdi.model.request.CardRequest;
import org.patikadev.finalprojectbatuhanunverdi.model.request.DepositRequest;
import org.patikadev.finalprojectbatuhanunverdi.model.request.PaymentRequest;

/**
 * @author Mert Batuhan UNVERDI
 * @since 2.06.2022
 */
public interface CardService {

    void createCard(CardRequest cardRequest);
    void paymentByCard(PaymentRequest paymentRequest);

    void depositMoney(DepositRequest depositRequest);
}
