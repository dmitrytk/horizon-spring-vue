package com.takkand.horizon.service;

import com.takkand.horizon.domain.Field;

import java.util.List;

public interface FieldService {
    Field getOne(Long id);

    List<Field> getAll();

    Field create(Field newField);

    Field update(Field newField);

    void delete(Field field);
}
