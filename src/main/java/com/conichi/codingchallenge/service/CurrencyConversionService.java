package com.conichi.codingchallenge.service;

import java.math.BigDecimal;

public interface CurrencyConversionService {

    BigDecimal convert(BigDecimal amount, String from, String to);
}
