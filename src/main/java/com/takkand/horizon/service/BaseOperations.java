package com.takkand.horizon.service;

import java.util.List;

public interface BaseOperations<T> {

    T findById(Long id);

    List<T> findAll();

    T create(T entity);

    T update(T entity);

    void delete(T entity);

    void deleteById(Long id);

    void deleteAll();
}
