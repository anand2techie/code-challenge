package com.conichi.codingchallenge.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "exchange-rates-api")
@Data
public class ExchangeRatesAPIConfig {

    private String hostname;

}
