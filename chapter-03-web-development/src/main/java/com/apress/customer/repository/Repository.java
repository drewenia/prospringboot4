package com.apress.customer.repository;

public interface Repository<T, ID> {
    T save(T entity);

    Iterable<T> findAll();

    T findById(ID id);

    void delete(ID id);
}
