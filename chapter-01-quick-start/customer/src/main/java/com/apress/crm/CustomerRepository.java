package com.apress.crm;

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
    public Customer findById(UUID uuid) {
        return customers.get(uuid);
    }

    @Override
    public Iterable<Customer> findAll() {
        return customers.values();
    }

    @Override
    public void deleteById(UUID uuid) {
        customers.remove(uuid);
    }
}
