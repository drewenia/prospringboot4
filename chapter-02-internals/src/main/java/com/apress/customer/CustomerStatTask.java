package com.apress.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.stream.StreamSupport;

@Component
public class CustomerStatTask {
    private static final Logger logger = LoggerFactory.getLogger(CustomerStatTask.class);
    private final Repository<Customer, ?> repository;

    public CustomerStatTask(Repository<Customer, ?> repository) {
        this.repository = repository;
    }

    @Scheduled(fixedRateString = "${crm.stats-rate}")
    public void reportCustomerCount() {
        long count = StreamSupport.stream(repository.findAll().spliterator(), false).count();
        logger.info("CRM Stats: Total customers currently in stream : {}", count);
    }
}
