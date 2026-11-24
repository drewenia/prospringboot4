package com.apress.customer.repository;

import com.apress.customer.model.Customer;
import com.apress.customer.rowMapper.CustomerRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.UUID;

public class CustomerRepository implements Repository<Customer, UUID> {
    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper rowMapper = new CustomerRowMapper();

    public CustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer findById(UUID id) {
        String query = "SELECT * FROM customer WHERE id=?";
        List<Customer> result = jdbcTemplate.query(query, rowMapper, id);
        return result.stream().findFirst().orElse(null);
    }

    @Override
    public Iterable<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customer", rowMapper);
    }

    @Override
    public void deleteById(UUID id) {
        jdbcTemplate.update("DELETE FROM customer WHERE id=?", id);
    }

    @Override
    public Customer save(Customer entity) {
        // If ID is null, generate one. Records are immutable, so we create a new instance
        if (entity.id() == null) {
            var newCustomer = new Customer(
                    UUID.randomUUID(),
                    entity.name(),
                    entity.email(),
                    entity.phone()
            );
            create(newCustomer);
            return newCustomer;
        }

        // If ID exists, check if it's an update
        if (findById(entity.id()) != null) {
            update(entity);
            return entity;
        }

        // ID exists but record doesn't, so create it (e.g. predefined UUID)
        create(entity);
        return entity;
    }

    private void create(Customer customer) {
        String sql = "INSERT INTO customer(id, name, email, phone) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql, customer.id(), customer.name(), customer.email(), customer.phone());
    }

    private void update(Customer customer) {
        String query = "UPDATE customer SET name=?, email=?, phone=? WHERE id=?";
        jdbcTemplate.update(query,customer.name(),customer.email(),customer.phone(),customer.id());
    }
}
