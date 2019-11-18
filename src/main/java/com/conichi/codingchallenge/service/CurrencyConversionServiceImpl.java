package com.conichi.codingchallenge.service;

import com.conichi.codingchallenge.entity.ExchangeRate;
import com.conichi.codingchallenge.exception.ConversionNotSupportedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class CurrencyConversionServiceImpl implements CurrencyConversionService {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Override
    public BigDecimal convert(BigDecimal amount, String from, String to) {
        LOG.info("Convert amount {} from: {} to: {}", amount, from, to);
        ExchangeRate exchangeRate = exchangeRateService.getExchangeRate(from, to);
        if (exchangeRate == null) {
            LOG.error("Currency conversion not supported from: {} to: {}", from, to);
            throw new ConversionNotSupportedException(
                "Currency conversion not supported from: " + from + " to: " + to);
        }
        return exchangeRate.getRate().multiply(amount);
    }
}