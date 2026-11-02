package com.apress.management.repository;

import com.apress.management.model.Company;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CompanyRepository implements Repository<Company, UUID> {
    private final ConcurrentHashMap<UUID, Company> companies = new ConcurrentHashMap<>();

    @Override
    public Company save(Company entity) {
        if (entity.companyId() == null) {
            entity = new Company(
                    UUID.randomUUID(),
                    entity.companyName(),
                    entity.industry(),
                    entity.website()
            );
        }
        companies.put(entity.companyId(), entity);
        return entity;
    }

    @Override
    public Company findById(UUID id) {
        return companies.get(id);
    }

    @Override
    public Iterable<Company> findAll() {
        return companies.values();
    }

    @Override
    public void deleteById(UUID id) {
        companies.remove(id);
    }
}
