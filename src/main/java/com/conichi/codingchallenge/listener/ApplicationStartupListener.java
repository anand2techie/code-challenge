package com.conichi.codingchallenge.listener;

import com.conichi.codingchallenge.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupListener implements
    ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        exchangeRateService.importExchangeRates();
    }
}