package org.patikadev.finalprojectbatuhanunverdi.model.request;

import lombok.Getter;
import lombok.Setter;
import org.patikadev.finalprojectbatuhanunverdi.entity.enums.CardType;

import java.math.BigDecimal;

/**
 * @author Mert Batuhan UNVERDI
 * @since 2.06.2022
 */
@Getter
@Setter
public class CardRequest {
    private Long account;
    private CardType cardType;
    private int cardPassword;
    private BigDecimal cardLimit;
}
