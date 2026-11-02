package com.apress.management.repository

import com.apress.management.model.Address
import org.springframework.stereotype.Component
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import java.util.stream.Collectors

@Component
class AddressRepository : Repository<Address, UUID> {
    val addresses = ConcurrentHashMap<UUID, Address>()

    override fun save(entity: Address): Address {
        val savedEntity = if (entity.addressId == null) {
            entity.copy(addressId = UUID.randomUUID())
        } else {
            entity
        }
        // !! operatörü İçin rahat olsun, ben bunun null olmadığını biliyorum
        addresses[savedEntity.addressId!!] = savedEntity
        return savedEntity
    }

    override fun findById(id: UUID): Address? {
        return addresses[id]
    }

    override fun findAll(): Iterable<Address> {
        return addresses.values
    }

    override fun deleteById(id: UUID) {
        addresses.remove(id)
    }

    fun findAllByCustomerId(customerId: UUID): Iterable<Address> {
        return this.addresses.values.stream()
            .filter { address -> address.customerId == customerId }
            .collect(Collectors.toList())
    }
}