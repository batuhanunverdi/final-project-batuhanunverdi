package org.patikadev.finalprojectbatuhanunverdi.model.request;

import lombok.Getter;
import lombok.Setter;
import org.patikadev.finalprojectbatuhanunverdi.entity.enums.AccountCurrency;

import java.math.BigDecimal;

/**
 * @author Mert Batuhan UNVERDI
 * @since 2.06.2022
 */
@Getter
@Setter
public class PaymentRequest {

    private String cardNumber;
    private int cardPassword;
    private BigDecimal price;
    private String receiverIban;
    private String receiverName;
    private AccountCurrency accountCurrency;
}
