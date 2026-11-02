package com.apress.crm

import org.springframework.stereotype.Component
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

@Component
class CustomerRepository : Repository<Customer, UUID>{

    val customers = ConcurrentHashMap<UUID, Customer>()

    override fun save(entity: Customer) : Customer {
        customers[entity.id] = entity
        return entity
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
}