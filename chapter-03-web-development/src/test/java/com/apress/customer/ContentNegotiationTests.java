package com.apress.customer;

import com.apress.customer.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
public class ContentNegotiationTests {
    private static final String APPLICATION_XML_UTF8_VALUE = "application/xml;charset=UTF-8";

    @Autowired
    private RestTestClient client;

    @Test
    void shouldReturnJsonByDefault() {
        client.get()
                .uri("/api/v1/customers")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Customer[].class)
                .consumeWith(entityExchangeResult -> {
                    Customer[] customers = entityExchangeResult.getResponseBody();
                    assertThat(customers).isNotNull();
                    for (Customer customer : customers) {
                        assertThat(customer.id()).isNotNull();
                    }
                });
    }

    @Test
    void shouldReturnXMLByDefault() {
        client.get()
                .uri("/api/v1/customers")
                .accept(MediaType.APPLICATION_XML)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_XML_UTF8_VALUE)
                .expectBody(Customer[].class)
                .consumeWith(entityExchangeResult -> {
                    Customer[] customers = entityExchangeResult.getResponseBody();
                    assertThat(customers).isNotNull();
                    for (Customer customer : customers){
                        assertThat(customer.id()).isNotNull();
                    }
                });
    }
}
