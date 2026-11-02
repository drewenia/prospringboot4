package com.apress.management.repository;

import com.apress.management.model.Customer;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class CustomerRepository implements Repository<Customer, UUID> {
    private final ConcurrentHashMap<UUID, Customer> customers = new ConcurrentHashMap<>();

    @Override
    public Customer save(Customer entity) {
        if (entity.customerId() == null) {
            entity = new Customer(
                    UUID.randomUUID(),
                    entity.companyId(),
                    entity.firstName(),
                    entity.lastName(),
                    entity.jobTitle(),
                    entity.email(),
                    entity.phone()
            );
        }
        customers.put(entity.customerId(), entity);
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
    public void deleteById(UUID id) {
        customers.remove(id);
    }

    public Iterable<Customer> findAllByCompanyId(UUID companyId) {
        return customers.values().stream()
                .filter(customer -> customer.companyId().equals(companyId))
                .collect(Collectors.toList());
    }
}
