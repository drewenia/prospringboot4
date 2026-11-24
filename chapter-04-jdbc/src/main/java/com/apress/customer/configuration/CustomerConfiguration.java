package com.apress.customer.configuration;

import com.apress.customer.model.Customer;
import com.apress.customer.repository.CustomerRepository;
import com.apress.customer.repository.Repository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.UUID;

@Configuration
public class CustomerConfiguration {
    @Bean
    CustomerRepository customerRepository(JdbcTemplate jdbcTemplate) {
        return new CustomerRepository(jdbcTemplate);
    }

    @Bean
    ApplicationListener<ApplicationReadyEvent> customerAppReady(Repository<Customer, UUID> repository) {
        return event -> {
            if (!repository.findAll().iterator().hasNext()){
                repository.save(new Customer("John","john@example.com","1-800-PHONE"));
                repository.save(new Customer("Jack","jack@example.com","1-800-PHONE"));
                repository.save(new Customer("Jane","jane@example.com","1-800-PHONE"));
            }
            repository.findAll().forEach(System.out::println);
        };
    }
}
