package com.conichi.codingchallenge.service;

import com.conichi.codingchallenge.entity.ExchangeRate;

public interface ExchangeRateService {

    ExchangeRate getExchangeRate(String from, String to);

    void importExchangeRates();
}