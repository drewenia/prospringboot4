package com.apress.management.repository

interface Repository<T,ID> {
    fun save(entity : T) : T
    fun findById(id : ID) : T?
    fun findAll() : Iterable<T>
    fun deleteById(id : ID)
}