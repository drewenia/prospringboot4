package com.apress.customer;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.SerializationFeature;

import java.util.UUID;

@Configuration
@Profile("!prod")
public class CustomerConfiguration {
    @Bean
    ApplicationListener<ApplicationReadyEvent> customerAppReady(Repository<Customer, UUID> repository){
        return event -> {
            repository.save(new Customer("John", "john@example.com", "1-800-PHONE"));
            repository.save(new Customer("Jane", "jane@example.com", "1-800-PHONE"));
            repository.save(new Customer("Jack", "jack@example.com", "1-800-PHONE"));
            repository.findAll().forEach(System.out::println);
        };
    }

    @Bean
    JsonMapperBuilderCustomizer jsonCustomizer(){
        return builder ->
                builder
                        .enable(SerializationFeature.INDENT_OUTPUT)
                        .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }
}
