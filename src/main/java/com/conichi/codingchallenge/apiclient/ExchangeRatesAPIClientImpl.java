package com.conichi.codingchallenge.apiclient;

import com.conichi.codingchallenge.config.ExchangeRatesAPIConfig;
import com.conichi.codingchallenge.model.external.ExchangeRateDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static com.conichi.codingchallenge.constant.ExchangeRatesAPIConstants.PARAM_BASE;
import static com.conichi.codingchallenge.constant.ExchangeRatesAPIConstants.PARAM_SYMBOLS;

@Component
@Slf4j
public class ExchangeRatesAPIClientImpl implements ExchangeRatesAPIClient {

    @Autowired
    private ExchangeRatesAPIConfig exchangeRatesAPIConfig;

    @Override
    public ExchangeRateDTO getExchangeRates(String from, String to) {
        LOG.info("Get exchange rates for currency from: {} to: {}", from, to);
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(exchangeRatesAPIConfig.getHostname())
            .queryParam(PARAM_BASE, from)
            .queryParam(PARAM_SYMBOLS, to);

        ResponseEntity<ExchangeRateDTO> exchange =
            restTemplate.getForEntity(builder.toUriString(), ExchangeRateDTO.class);
        return exchange.getBody();
    }
}
