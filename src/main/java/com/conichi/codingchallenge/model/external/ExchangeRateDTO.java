package com.conichi.codingchallenge.model.external;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Data
public class ExchangeRateDTO {

    private Map<String, BigDecimal> rates;

    private String base;

    private Date date;
}
