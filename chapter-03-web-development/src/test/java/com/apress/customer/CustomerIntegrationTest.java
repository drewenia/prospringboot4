package com.apress.customer;

import com.apress.customer.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
public class CustomerIntegrationTest {
    @Autowired
    RestTestClient client;

    @Test
    void shouldCreateCustomer() {
        Customer customer = new Customer(
                "John Doe",
                "john.doe@example.com",
                "123456789"
        );
        var result = client.post()
                .uri("/api/v1/customers")
                .body(customer)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists("Location")
                .returnResult(Void.class);

        String location = result.getResponseHeaders().getFirst("Location");
        System.out.println(location); // http://localhost:57328/api/v1/customers/40451e6f-d59b-44aa-bef5-20149a47b3f9
    }
}
