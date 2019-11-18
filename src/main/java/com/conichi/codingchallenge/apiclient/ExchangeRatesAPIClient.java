package com.conichi.codingchallenge.apiclient;

import com.conichi.codingchallenge.model.external.ExchangeRateDTO;

public interface ExchangeRatesAPIClient {

    ExchangeRateDTO getExchangeRates(String from, String to);
}
