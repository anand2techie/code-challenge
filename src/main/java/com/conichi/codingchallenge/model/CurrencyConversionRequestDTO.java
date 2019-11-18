package com.conichi.codingchallenge.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyConversionRequestDTO {

    @NotNull
    private BigDecimal amount;

    @NotEmpty
    private String from;

    @NotEmpty
    private String to;
}