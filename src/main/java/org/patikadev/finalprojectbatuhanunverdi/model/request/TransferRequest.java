package org.patikadev.finalprojectbatuhanunverdi.model.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author Mert Batuhan UNVERDI
 * @since 21.05.2022
 */
@Getter
@Setter
public class TransferRequest {
    private String senderIBan;
    private String receiverIBan;
    private BigDecimal balance;
}
