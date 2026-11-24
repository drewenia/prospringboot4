package com.apress.customer;

import com.apress.customer.model.Customer;
import com.apress.customer.repository.CustomerRepository;
import com.apress.customer.repository.JdbcClientCustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(JdbcClientCustomerRepository.class)
public class CustomerRepositoryTests {
    @Autowired
    private JdbcClientCustomerRepository repository;

    @Test
    void shouldPersistAndRetrieveCustomer() {
        Customer customer = new Customer(
                UUID.randomUUID(),
                "John Doe",
                "john@example.com",
                "555-9990"
        );
        repository.save(customer);
        Customer found = repository.findById(customer.id());
        assertThat(found).isNotNull();
        assertThat(customer.email()).isEqualTo("john@example.com");
    }
}
