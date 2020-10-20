package com.takkand.horizon.service;

import java.util.List;
import java.util.Optional;

public interface BaseOperations<T> {

    // FIND
    Optional<T> findById(Long id);

    List<T> findAll();

    // CREATE & UPDATE
    T create(T entity);

    T update(T entity);


    // DELETE
    void delete(T entity);

    void deleteById(Long id);

    void deleteAll();

    void deleteInclinometry(Long id);

    void deleteMer(Long id);

    void deleteRates(Long id);

}
