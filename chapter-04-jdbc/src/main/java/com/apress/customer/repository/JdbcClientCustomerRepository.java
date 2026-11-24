package com.apress.customer.repository;

import com.apress.customer.model.Customer;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.UUID;

public class JdbcClientCustomerRepository implements Repository<Customer, UUID> {

    private final JdbcClient jdbcClient;

    public JdbcClientCustomerRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Iterable<Customer> findAll() {
        return jdbcClient.sql("SELECT * FROM customer")
                .query(Customer.class) // (1)
                .list();
    }

    @Override
    public Customer findById(UUID id) {
        return jdbcClient.sql("SELECT * FROM customer WHERE id=:id")
                .param("id", id) // 2
                .query(Customer.class)
                .optional()
                .orElse(null);
    }

    @Override
    public Customer save(Customer entity) {
        if (entity.id() == null) {
            Customer newCustomer = new Customer(
                    UUID.randomUUID(),
                    entity.name(),
                    entity.email(),
                    entity.phone()
            );
            create(newCustomer);
            return newCustomer;
        }
        if (findById(entity.id()) != null) {
            update(entity);
            return entity;
        }
        create(entity);
        return entity;
    }

    private void create(Customer customer) {
        jdbcClient.sql("INSERT INTO customer (id, name, email, phone) VALUES (:id,:name,:email,:phone)")
                .param("id", customer.id())
                .param("name", customer.name())
                .param("email", customer.email())
                .param("phone", customer.phone())
                .update();
    }

    private void update(Customer customer) {
        jdbcClient.sql("UPDATE customer SET name=:name, email=:email, phone=:phone WHERE id=:id")
                .param("name", customer.name())
                .param("email", customer.email())
                .param("phone", customer.phone())
                .param("id", customer.id())
                .update();
    }

    @Override
    public void deleteById(UUID id) {
        jdbcClient.sql("DELETE FROM customer WHERE id=:id")
                .param("id", id)
                .update();
    }
}

