package com.conichi.codingchallenge.mapper;

import com.conichi.codingchallenge.entity.ExchangeRate;
import com.conichi.codingchallenge.model.external.ExchangeRateDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExchangeRateDTOToEntityMapper {

    public List<ExchangeRate> map(ExchangeRateDTO exchangeRateDTO) {
        List<ExchangeRate> exchangeRateList = new ArrayList<>();
        exchangeRateDTO.getRates().entrySet().stream().forEach(rate -> {
            ExchangeRate exchangeRate = new ExchangeRate();
            exchangeRate.setFromCurrency(exchangeRateDTO.getBase());
            exchangeRate.setToCurrency(rate.getKey());
            exchangeRate.setRate(rate.getValue());
            exchangeRateList.add(exchangeRate);
        });
        return exchangeRateList;
    }
}
