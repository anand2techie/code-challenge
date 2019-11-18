package com.conichi.codingchallenge.service;

import com.conichi.codingchallenge.entity.ExchangeRate;
import com.conichi.codingchallenge.exception.ConversionNotSupportedException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyConversionServiceImplTest {

    @InjectMocks
    private CurrencyConversionServiceImpl currencyConversionService;

    @Mock
    private ExchangeRateService exchangeRateService;

    @Test
    public void testSuccessfulConversion() {
        BigDecimal amount = new BigDecimal(100);
        BigDecimal rate = new BigDecimal(78.67);

        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setRate(rate);
        Mockito.when(exchangeRateService.getExchangeRate(Mockito.anyString(), Mockito.anyString()))
            .thenReturn(exchangeRate);

        BigDecimal result = currencyConversionService.convert(new BigDecimal(100), "EUR", "INR");

        Assert.assertEquals(rate.multiply(amount), result);
    }

    @Test(expected = ConversionNotSupportedException.class)
    public void testIfExchangeRateNotPresent() {
        Mockito.when(exchangeRateService.getExchangeRate(Mockito.anyString(), Mockito.anyString()))
            .thenReturn(null);
        currencyConversionService.convert(new BigDecimal(100), "EUR", "INR");
    }
}
