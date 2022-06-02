package org.patikadev.finalprojectbatuhanunverdi.model.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author Mert Batuhan UNVERDI
 * @since 3.06.2022
 */
@Getter
@Setter
public class DepositRequest {
    private String cardNumber;
    private int cardPassword;
    private BigDecimal amount;
}
