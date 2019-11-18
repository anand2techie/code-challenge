package com.conichi.codingchallenge.repository;

import com.conichi.codingchallenge.entity.ExchangeRate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends CrudRepository<ExchangeRate, Long> {

    ExchangeRate findByFromCurrencyAndToCurrency(String from, String to);
}