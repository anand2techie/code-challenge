package com.conichi.codingchallenge.scheduler;

import com.conichi.codingchallenge.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.conichi.codingchallenge.constant.SchedulerConstants.EVERY_DAY_AT_12_AM;

@Component
@EnableScheduling
public class ExchangeRateAPIScheduler {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Scheduled(cron = EVERY_DAY_AT_12_AM, zone = "Europe/Berlin")
    public void scheduledExhangeRatesImport() {
        exchangeRateService.importExchangeRates();
    }
}