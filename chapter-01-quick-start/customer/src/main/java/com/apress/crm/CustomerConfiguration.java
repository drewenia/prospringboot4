package com.apress.crm;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class CustomerConfiguration {
    @Bean
    CommandLineRunner customerInitializer(Repository<Customer, UUID> repository) {
        return args -> {
            repository.save(new Customer("John", "john@example.com", "1-800-PHONE"));
            repository.save(new Customer("Jane", "jane@example.com", "1-800-PHONE"));
            repository.save(new Customer("Jack", "jack@example.com", "1-800-PHONE"));
            repository.findAll().forEach(System.out::println);
        };
    }
}
