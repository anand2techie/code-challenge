package com.conichi.codingchallenge.service;

import com.conichi.codingchallenge.apiclient.ExchangeRatesAPIClient;
import com.conichi.codingchallenge.entity.ExchangeRate;
import com.conichi.codingchallenge.mapper.ExchangeRateDTOToEntityMapper;
import com.conichi.codingchallenge.model.external.ExchangeRateDTO;
import com.conichi.codingchallenge.repository.ExchangeRateRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ExchangeRateServiceImplTest {

    @InjectMocks
    private ExchangeRateServiceImpl exchangeRateService;

    @Mock
    private ExchangeRateRepository exchangeRateRepository;

    @Mock
    private ExchangeRatesAPIClient exchangeRatesAPIClient;

    @Mock
    private ExchangeRateDTOToEntityMapper exchangeRateDTOToEntityMapper;

    @Test
    public void testImportExchangeRates() {
        BigDecimal rate = new BigDecimal(78.67);
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setRate(rate);

        List<ExchangeRate> exchangeRates = new ArrayList<>();
        exchangeRates.add(exchangeRate);
        Mockito.when(exchangeRateRepository.findAll()).thenReturn(exchangeRates);

        ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO();
        Mockito.when(exchangeRatesAPIClient.getExchangeRates("EUR", "")).thenReturn(exchangeRateDTO);
        Mockito.when(exchangeRatesAPIClient.getExchangeRates(exchangeRate.getToCurrency(), ""))
            .thenReturn(exchangeRateDTO);

        exchangeRateService.importExchangeRates();

        verify(exchangeRateRepository, times(2)).saveAll(anyList());
        verify(exchangeRateRepository, times(1)).findAll();
        verify(exchangeRatesAPIClient, times(2)).getExchangeRates(any(), any());
    }

    @Test
    public void testGetExchangeRates() {
        BigDecimal rate = new BigDecimal(78.67);
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setRate(rate);
        exchangeRate.setFromCurrency("EUR");
        exchangeRate.setFromCurrency("INR");

        Mockito.when(exchangeRateRepository.findByFromCurrencyAndToCurrency(any(), any())).thenReturn(exchangeRate);

        ExchangeRate result = exchangeRateService.getExchangeRate("EUR", "INR");

        verify(exchangeRateRepository, times(1)).findByFromCurrencyAndToCurrency(any(), any());
        Assert.assertEquals(exchangeRate.getRate(), result.getRate());
        Assert.assertEquals(exchangeRate.getFromCurrency(), result.getFromCurrency());
        Assert.assertEquals(exchangeRate.getToCurrency(), result.getToCurrency());
    }
}
