package com.apress.crm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
@DirtiesContext
public class CustomerApplicationTest {
    @Autowired
    RestTestClient restTestClient;

    @Test
    void shouldRetrieveAndCreateCustomers() {
        restTestClient.get()
                .uri("/api/v1/customers")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Customer[].class)
                .value(customers -> assertThat(customers).hasSize(3));

        Customer customer = new Customer(
                "New User",
                "newuser@example.com",
                "555-1199"
        );

        restTestClient.post()
                .uri("/api/v1/customers")
                .body(customer)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Customer.class)
                .value(savedCustomer -> {
                    assert savedCustomer != null;
                    assertThat(savedCustomer.id()).isNotNull();
                    assertThat(savedCustomer.id()).isEqualTo(customer.id());
                    assertThat(savedCustomer.name()).isEqualTo(customer.name());
                    assertThat(savedCustomer.email()).isEqualTo(customer.email());
                    assertThat(savedCustomer.phone()).isEqualTo(customer.phone());
                });

        restTestClient.delete()
                .uri("/api/v1/customers/{id}", customer.id())
                .exchange()
                .expectStatus().isOk();

        restTestClient.get()
                .uri("/api/v1/customers")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Customer[].class)
                .value(customers -> assertThat(customers).hasSize(3));
    }
}
