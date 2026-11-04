package com.apress.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CustomerRepository implements Repository<Customer, UUID> {
    private static final Logger logger = LoggerFactory.getLogger(CustomerRepository.class);
    private final ConcurrentHashMap<UUID, Customer> customers = new ConcurrentHashMap<>();

    @Override
    public Customer save(Customer entity) {
        logger.debug("Saving customer {}", entity.name());
        if (entity.id() == null) {
            entity = new Customer(
                    entity.name(),
                    entity.email(),
                    entity.phone()
            );
        }
        customers.put(entity.id(), entity);
        return entity;
    }

    @Override
    public Customer findById(UUID id) {
        return customers.get(id);
    }

    @Override
    public Iterable<Customer> findAll() {
        return customers.values();
    }

    @Override
    public void delete(UUID id) {
        customers.remove(id);
    }
}
