package org.patikadev.finalprojectbatuhanunverdi.service.implementation;

import lombok.RequiredArgsConstructor;
import org.patikadev.finalprojectbatuhanunverdi.model.ExchangeModel;
import org.patikadev.finalprojectbatuhanunverdi.service.ExchangeService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Mert Batuhan UNVERDI
 * @since 21.05.2022
 */
@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {
    private final RestTemplate restTemplate;

    @Override
    public ResponseEntity<ExchangeModel> getExchangeByBaseCurrency(String baseCurrency) {
        String api = "https://api.apilayer.com/exchangerates_data/latest?symbols=EUR,USD,TRY&base=" + baseCurrency;
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", "JuINwbHj9k49y9ijXGi5PP28V2PrR0We");
        HttpEntity<String> httpEntity = new HttpEntity<>("apikey", headers);
        ResponseEntity<ExchangeModel> exchangeModelResponseEntity = restTemplate.exchange(api, HttpMethod.GET, httpEntity, ExchangeModel.class);
        ExchangeModel exchangeModel = exchangeModelResponseEntity.getBody();
        return ResponseEntity.ok(exchangeModel);
    }
}
