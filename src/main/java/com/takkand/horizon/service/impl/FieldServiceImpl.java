package com.takkand.horizon.service.impl;

import com.takkand.horizon.domain.Field;
import com.takkand.horizon.repository.FieldRepository;
import com.takkand.horizon.service.FieldService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FieldServiceImpl implements FieldService {
    private final FieldRepository fieldRepository;

    public FieldServiceImpl(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    @Override
    public Field create(Field newField) {
        return fieldRepository.save(newField);
    }

    @Override
    public Field update(Field newField) {
        return fieldRepository.save(newField);
    }

    @Override
    public void delete(Field field) {
        fieldRepository.delete(field);
    }

    @Override
    public void deleteById(Long id) {
        fieldRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        fieldRepository.deleteAll();
    }

    @Override
    public Field findById(Long id) {
        return fieldRepository.getOne(id);
    }

    @Override
    public List<Field> findAll() {
        return fieldRepository.findAll();
    }
}
