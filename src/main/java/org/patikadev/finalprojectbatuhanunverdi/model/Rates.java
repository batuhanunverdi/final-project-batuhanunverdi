package org.patikadev.finalprojectbatuhanunverdi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.patikadev.finalprojectbatuhanunverdi.exception.BusinessServiceOperationException;

import java.math.BigDecimal;

/**
 * @author Mert Batuhan UNVERDI
 * @since 21.05.2022
 */
@Getter
@Setter
public class Rates {
    @JsonProperty("TRY")
    private double TRY;
    @JsonProperty("USD")
    private double USD;
    @JsonProperty("EUR")
    private double EUR;

    public BigDecimal exchangeValueByReceiverCurrency(String currency) {
        if (currency.equals("EUR")) {
            return BigDecimal.valueOf(getEUR());
        }
        if (currency.equals("USD")) {
            return BigDecimal.valueOf(getUSD());
        }
        if (currency.equals("TRY")) {
            return BigDecimal.valueOf(getTRY());
        } else {
            throw new BusinessServiceOperationException.ExchangeNotFound("Exchange type not found");
        }
    }
}
