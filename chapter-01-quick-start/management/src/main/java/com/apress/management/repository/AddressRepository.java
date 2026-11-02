package com.apress.management.repository;

import com.apress.management.model.Address;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class AddressRepository implements Repository<Address, UUID>{
    private final ConcurrentHashMap<UUID,Address> addresses = new ConcurrentHashMap<>();

    @Override
    public Address save(Address entity) {
        if (entity.addressId() == null){
            var addressId = UUID.randomUUID();
            entity = new Address(
                    addressId,
                    entity.customerId(),
                    entity.street(),
                    entity.city(),
                    entity.state(),
                    entity.zip()
            );
        }
        this.addresses.put(entity.addressId(),entity);
        return entity;
    }

    @Override
    public Address findById(UUID id) {
        return addresses.get(id);
    }

    @Override
    public Iterable<Address> findAll() {
        return addresses.values();
    }

    @Override
    public void deleteById(UUID id) {
        addresses.remove(id);
    }

    public Iterable<Address> findAllByCustomerId(UUID customerId){
        return this.addresses.values().stream()
                .filter(address -> address.customerId().equals(customerId))
                .collect(Collectors.toList());
    }
}
