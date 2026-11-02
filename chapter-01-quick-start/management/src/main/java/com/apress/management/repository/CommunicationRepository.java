package com.apress.management.repository;

import com.apress.management.model.Communication;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class CommunicationRepository implements Repository<Communication, UUID> {
    private final ConcurrentHashMap<UUID, Communication> communications = new ConcurrentHashMap<>();

    @Override
    public Communication save(Communication entity) {
        if (entity.communicationId() == null) {
            entity = new Communication(
                    UUID.randomUUID(),
                    entity.customerId(),
                    entity.communicationType(),
                    entity.communicationValue()
            );
        }
        communications.put(entity.communicationId(), entity);
        return entity;
    }

    @Override
    public Communication findById(UUID id) {
        return communications.get(id);
    }

    @Override
    public Iterable<Communication> findAll() {
        return communications.values();
    }

    @Override
    public void deleteById(UUID id) {
        communications.remove(id);
    }

    public Iterable<Communication> findAllByCustomerId(UUID customerId) {
        return communications.values().stream()
                .filter(communication -> communication.customerId().equals(customerId))
                .collect(Collectors.toList());
    }
}
