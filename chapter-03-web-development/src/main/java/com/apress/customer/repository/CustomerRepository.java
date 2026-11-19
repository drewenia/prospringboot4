package com.apress.customer.repository;

import com.apress.customer.model.Customer;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CustomerRepository implements Repository<Customer, UUID> {
    private final ConcurrentHashMap<UUID, Customer> customers = new ConcurrentHashMap<>();

    @Override
    public Customer save(Customer entity) {
        if (entity.id() == null) {
            entity = new Customer(
                    entity.name(),
                    entity.email(),
                    entity.phone()
            );
        }
        customers.put(entity.id(),entity);
        return entity;
    }

    @Override
    public Iterable<Customer> findAll() {
        return customers.values();
    }

    @Override
    public Customer findById(UUID id) {
        return customers.get(id);
    }

    @Override
    public void delete(UUID id) {
        customers.remove(id);
    }
}
