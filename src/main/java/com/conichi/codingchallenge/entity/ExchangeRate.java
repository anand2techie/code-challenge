package com.conichi.codingchallenge.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "exchange_rate")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExchangeRate extends EntityBase {

    private String fromCurrency;

    private String toCurrency;

    private BigDecimal rate;

}
