package com.apress.management.configuration;

import com.apress.management.model.Address;
import com.apress.management.model.Communication;
import com.apress.management.model.Company;
import com.apress.management.model.Customer;
import com.apress.management.reactive.ManagementHandlers;
import com.apress.management.service.ManagementService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ManagementConfiguration {
    @Bean
    ApplicationListener<ApplicationReadyEvent> managementReady(ManagementService service) {
        return event -> {
            var address1 = new Address(
                    null,
                    null,
                    "123. Main St.",
                    "New York",
                    "NY",
                    "10001"
            );
            var communication1 = new Communication(
                    null,
                    null,
                    "email",
                    "felipe.gomez@apress.com"
            );
            var company1 = new Company(
                    null,
                    "Apress",
                    "Publishing",
                    "apress.com"
            );
            var customer1 = new Customer(
                    null,
                    null,
                    "Felipe",
                    "Gomez",
                    "Author",
                    "felipe.gomez@apress.com",
                    "123-4567890"
            );

            System.out.println("Creating customer 1 : " + service.createCustomerWithDetails(
                    address1,
                    communication1,
                    company1,
                    customer1
            ));

            var address2 = new Address(
                    null,
                    null,
                    "456 Oak Ave",
                    "San Francisco",
                    "CA",
                    "94102"
            );
            var communication2 = new Communication(
                    null,
                    null,
                    "twitter",
                    "@starbuxman"
            );
            var company2 = new Company(
                    null,
                    "Spring",
                    "Software",
                    "spring.io"
            );
            var customer2 = new Customer(
                    null,
                    null,
                    "Josh",
                    "Long",
                    "Developer",
                    "josh.long@spring.io",
                    "098-765-4321"
            );

            System.out.println("Creating customer 2 : " + service.createCustomerWithDetails(
                    address2,
                    communication2,
                    company2,
                    customer2
            ));

            var address3 = new Address(
                    null,
                    null,
                    "789 Pine Ln",
                    "Mountain View",
                    "CA",
                    "94043"
            );
            var communication3 = new Communication(
                    null,
                    null,
                    "email",
                    "sundar.pichai@google.com"
            );
            var company3 = new Company(
                    null,
                    "Google",
                    "Technology",
                    "google.com"
            );
            var customer3 = new Customer(
                    null,
                    null,
                    "Sundar",
                    "Pichai",
                    "CEO",
                    "sundar.pichai@google.com",
                    "111-222-3333"
            );

            System.out.println("Creating customer 3 : " + service.createCustomerWithDetails(
                    address3,
                    communication3,
                    company3,
                    customer3
            ));
        };
    }

    @Bean
    RouterFunction<ServerResponse> managementRoutes(ManagementHandlers handlers) {
        return route(RequestPredicates.GET("/api/v1/customers/{id}"),handlers::getCustomerDetails)
                .andRoute(RequestPredicates.GET("/api/v1/customers"),handlers::getCustomers);
    }
}
