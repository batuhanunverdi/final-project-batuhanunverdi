package org.patikadev.finalprojectbatuhanunverdi.service;

import org.patikadev.finalprojectbatuhanunverdi.model.ExchangeModel;
import org.springframework.http.ResponseEntity;

/**
 * @author Mert Batuhan UNVERDI
 * @since 21.05.2022
 */
public interface ExchangeService {
    ResponseEntity<ExchangeModel> getExchangeByBaseCurrency(String baseCurrency);
}
