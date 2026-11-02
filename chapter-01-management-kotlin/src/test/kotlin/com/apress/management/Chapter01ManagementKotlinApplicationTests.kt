package com.apress.management

import com.apress.management.dto.CustomerDetailsDTO
import com.apress.management.repository.CustomerRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import java.util.UUID

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class Chapter01ManagementKotlinApplicationTests(
    @Autowired private val webTestClient: WebTestClient,
    @Autowired private val customerRepository: CustomerRepository
) {
    @Test
    fun shouldRetrieveCustomerDetails() {
        val customers = customerRepository.findAll()
        assertThat(customers).isNotEmpty
        assertThat(customers).hasSize(1)

        val customer = customers.iterator().next()
        webTestClient.get()
            .uri("/api/v1/customers/" + customer.customerId)
            .exchange()
            .expectStatus().isOk
            .expectBody<CustomerDetailsDTO>()
    }

    @Test
    fun shouldReturnNotFoundForInvalidId() {
        webTestClient.get()
            .uri("/api/v1/customers/" + UUID.randomUUID())
            .exchange()
            .expectStatus().isNotFound
    }
}
