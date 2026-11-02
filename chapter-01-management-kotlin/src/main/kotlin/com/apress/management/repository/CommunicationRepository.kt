package com.apress.management.repository

import com.apress.management.model.Communication
import org.springframework.stereotype.Component
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import java.util.stream.Collectors

@Component
class CommunicationRepository : Repository<Communication, UUID> {
    val communications = ConcurrentHashMap<UUID, Communication>()

    override fun save(entity: Communication): Communication {
        val savedEntity = if(entity.communicationId == null){
            entity.copy(communicationId = UUID.randomUUID())
        } else {
            entity
        }
        communications[savedEntity.communicationId!!] = savedEntity
        return savedEntity
    }

    override fun findById(id: UUID): Communication? {
        return communications[id]
    }

    override fun findAll(): Iterable<Communication> {
        return communications.values
    }

    override fun deleteById(id: UUID) {
        communications.remove(id)
    }

    fun findAllByCustomerId(customerId: UUID) : Iterable<Communication> {
        return this.communications.values.stream()
            .filter { comm -> comm.customerId == customerId }
            .collect(Collectors.toList())
    }
}