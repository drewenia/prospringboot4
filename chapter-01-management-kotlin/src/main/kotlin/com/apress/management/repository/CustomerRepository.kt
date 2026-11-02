package com.apress.management.repository

import com.apress.management.model.Customer
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.stream.Collectors

@Component
class CustomerRepository : Repository<Customer, UUID> {
    val customers = ConcurrentHashMap<UUID, Customer>()

    override fun save(entity: Customer): Customer {
        val savedEntity = if (entity.customerId == null) {
            entity.copy(customerId = UUID.randomUUID())
        } else {
            entity
        }
        customers[savedEntity.customerId!!] = savedEntity
        return savedEntity
    }

    override fun findById(id: UUID): Customer? {
        return customers[id]
    }

    override fun findAll(): Iterable<Customer> {
        return customers.values
    }

    override fun deleteById(id: UUID) {
        customers.remove(id)
    }

    fun findAllByCompanyId(companyId: UUID): Iterable<Customer> {
        return this.customers.values.stream()
            .filter { customer -> customer.companyId == companyId }
            .collect(Collectors.toList());
    }
}