package com.conichi.codingchallenge.service;

import com.conichi.codingchallenge.apiclient.ExchangeRatesAPIClient;
import com.conichi.codingchallenge.entity.ExchangeRate;
import com.conichi.codingchallenge.mapper.ExchangeRateDTOToEntityMapper;
import com.conichi.codingchallenge.model.external.ExchangeRateDTO;
import com.conichi.codingchallenge.repository.ExchangeRateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExchangeRateServiceImpl implements ExchangeRateService {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private ExchangeRatesAPIClient exchangeRatesAPIClient;

    @Autowired
    private ExchangeRateDTOToEntityMapper exchangeRateDTOToEntityMapper;

    @Override
    public ExchangeRate getExchangeRate(String from, String to) {
        return exchangeRateRepository.findByFromCurrencyAndToCurrency(from, to);
    }

    @Override
    public void importExchangeRates() {
        LOG.info("Start - Import exchange rates from third party API");
        exchangeRateRepository.deleteAll();

        //fetch all exchange rates for EUR
        ExchangeRateDTO exchangeRateDTO = exchangeRatesAPIClient.getExchangeRates("EUR", "");
        exchangeRateRepository.saveAll(exchangeRateDTOToEntityMapper.map(exchangeRateDTO));

        //fetch all exchange rates for other supported currencies
        exchangeRateRepository.findAll().forEach(exchangeRate -> {
            ExchangeRateDTO exchangeRateDTOForOtherCurrencies =
                exchangeRatesAPIClient.getExchangeRates(exchangeRate.getToCurrency(), "");
            exchangeRateRepository.saveAll(exchangeRateDTOToEntityMapper.map(exchangeRateDTOForOtherCurrencies));
        });
        LOG.info("Done - Import exchange rates from third party API");
    }
}