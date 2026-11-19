package com.apress.customer;

import com.apress.customer.controller.CustomerController;
import com.apress.customer.model.Customer;
import com.apress.customer.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerControllerTest {
    private final RestTestClient client = RestTestClient
            .bindToController(new CustomerController(new CustomerRepository()))
            .build();

    @Test
    void shouldReturnAllCustomers(){
        client.get()
                .uri("/api/v1/customers")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Customer[].class)
                .value(customers -> assertThat(customers).isEmpty());
    }
}
