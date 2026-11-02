package com.apress.crm

interface Repository<T, ID> {
    fun save(entity: T) : T
    fun findById(id: ID): T?
    fun findAll(): Iterable<T>
    fun deleteById(id: ID)
}