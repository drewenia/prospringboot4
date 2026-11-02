package com.apress.management.repository

import com.apress.management.model.Company
import org.springframework.stereotype.Component
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

@Component
class CompanyRepository : Repository<Company, UUID> {
    val companies = ConcurrentHashMap<UUID, Company>()

    override fun save(entity: Company): Company {
        val savedEntity = if (entity.companyId == null) {
            entity.copy(companyId = UUID.randomUUID())
        } else {
            entity
        }
        companies[savedEntity.companyId!!] = savedEntity
        return savedEntity
    }

    override fun findById(id: UUID): Company? {
        return companies[id]
    }

    override fun findAll(): Iterable<Company> {
        return companies.values
    }

    override fun deleteById(id: UUID) {
        companies.remove(id)
    }
}