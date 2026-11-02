package com.apress.crm

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.client.RestTestClient
import org.springframework.test.web.servlet.client.expectBody

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
@DirtiesContext
class Chapter01QuickStartKotlinApplicationTests(@Autowired private val restTestClient: RestTestClient) {
    @Test
    fun shouldRetrieveAndCreateCustomers() {
        restTestClient
            .get()
            .uri("/api/v1/customers")
            .exchange()
            .expectStatus().isOk
            .expectBody<Array<Customer>>()
            .value { customers -> assertThat(customers).hasSize(3) }

        val customer = Customer("new user", "new_user@example.com", "444-PHONE")

        restTestClient
            .post()
            .uri("/api/v1/customers")
            .body(customer)
            .exchange()
            .expectStatus().isOk
            .expectBody<Customer>()
            .value { savedCustomer ->
                assertThat(savedCustomer?.id).isNotNull
                assertThat(savedCustomer?.name).isEqualTo(customer.name)
            }

        restTestClient
            .delete()
            .uri("/api/v1/customers/{id}",customer.id)
            .exchange()
            .expectStatus().isNoContent()

        restTestClient
            .get()
            .uri("/api/v1/customers")
            .exchange()
            .expectStatus().isOk
            .expectBody<Array<Customer>>()
            .value { customers -> assertThat(customers).hasSize(3) }
    }
}
