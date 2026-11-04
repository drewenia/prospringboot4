package com.apress.customer;

public interface Repository<T, ID> {
    T save(T entity);

    T findById(ID id);

    Iterable<T> findAll();

    void delete(ID id);
}
