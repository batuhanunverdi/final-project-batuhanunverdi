package org.patikadev.finalprojectbatuhanunverdi.model.request;

import lombok.Getter;
import lombok.Setter;
import org.patikadev.finalprojectbatuhanunverdi.entity.Account;

import java.math.BigDecimal;

/**
 * @author Mert Batuhan UNVERDI
 * @since 3.06.2022
 */
@Getter
@Setter
public class DebtPaymentRequest {
    private Long senderAccount;
    private Long receiverAccount;
    private BigDecimal amount;

}
