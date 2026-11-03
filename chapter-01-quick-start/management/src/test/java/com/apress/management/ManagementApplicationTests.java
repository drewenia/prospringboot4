package com.apress.management;

import com.apress.management.dto.CustomerDetailsDTO;
import com.apress.management.model.Customer;
import com.apress.management.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
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
    void shouldReturnNotFoundForInvalidId() {
        webTestClient.get()
                .uri("/api/v1/customers/" + UUID.randomUUID())
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void shouldRetrieveCustomerWithDetails() {
        webTestClient.get()
                .uri("/api/v1/customers")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(CustomerDetailsDTO[].class)
                .value(dto -> {
                    assert dto != null;
                    assertThat(dto).hasSize(3);

                    CustomerDetailsDTO felipe = Arrays.stream(dto)
                            .filter(p -> "Felipe".equals(p.customer().firstName()))
                            .findFirst()
                            .orElseThrow(() -> new AssertionError("Felipe not found"));
                    assertThat(felipe.customer().firstName()).isEqualTo("Felipe");
                    assertThat(felipe.customer().lastName()).isEqualTo("Gomez");
                    assertThat(felipe.customer().email()).isEqualTo("felipe.gomez@apress.com");
                    assertThat(felipe.company().companyName()).isEqualTo("Apress");
                    assertThat(felipe.addresses()).isNotEmpty();
                    assertThat(felipe.communications()).isNotEmpty();
                });
    }
}
