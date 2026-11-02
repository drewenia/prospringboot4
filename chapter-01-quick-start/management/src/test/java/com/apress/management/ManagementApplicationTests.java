package com.apress.management;

import com.apress.management.dto.CustomerDetailsDTO;
import com.apress.management.model.Customer;
import com.apress.management.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ManagementApplicationTests {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    CustomerRepository repository;
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void shouldRetrieveCustomerDetails() {
        Iterable<Customer> customers = repository.findAll();
        assertThat(customers).isNotEmpty();

        Customer customer = customers.iterator().next();

        webTestClient.get()
                .uri("/api/v1/customers/" + customer.customerId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerDetailsDTO.class)
                .value(dto -> {
                    assertThat(dto).isNotNull();
                    assertThat(dto.customer()).isNotNull();
                    assertThat(dto.customer().customerId()).isEqualTo(customer.customerId());
                    assertThat(dto.customer().firstName()).isEqualTo(customer.firstName());
                    assertThat(dto.customer().lastName()).isEqualTo(customer.lastName());
                    assertThat(dto.company()).isNotNull();
                    assertThat(dto.addresses()).isNotEmpty();
                    assertThat(dto.communications()).isNotEmpty();
                });
    }

    @Test
    void shouldReturnNotFoundForInvalidId(){
        webTestClient.get()
                .uri("/api/v1/customers/" + UUID.randomUUID())
                .exchange()
                .expectStatus().isNotFound();
    }
}
