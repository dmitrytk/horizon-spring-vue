package com.takkand.horizon.service.impl;

import com.takkand.horizon.domain.Field;
import com.takkand.horizon.repository.FieldRepository;
import com.takkand.horizon.repository.InclinometryRepository;
import com.takkand.horizon.repository.MerRepository;
import com.takkand.horizon.repository.RateRepository;
import com.takkand.horizon.service.FieldService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FieldServiceImpl implements FieldService {
    private final FieldRepository fieldRepository;
    private final InclinometryRepository inclinometryRepository;
    private final MerRepository merRepository;
    private final RateRepository rateRepository;

    public FieldServiceImpl(FieldRepository fieldRepository, InclinometryRepository inclinometryRepository, MerRepository merRepository, RateRepository rateRepository) {
        this.fieldRepository = fieldRepository;
        this.inclinometryRepository = inclinometryRepository;
        this.merRepository = merRepository;
        this.rateRepository = rateRepository;
    }

    @Override
    public List<Field> findAll() {
        return fieldRepository.findAll();
    }

    @Override
    public Optional<Field> findById(Long id) {
        return fieldRepository.findById(id);
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
    public void deleteInclinometry(Long id) {
        inclinometryRepository.deleteFieldInclinometry(id);
    }

    @Override
    public void deleteMer(Long id) {
        merRepository.deleteFieldMer(id);
    }

    @Override
    public void deleteRates(Long id) {
        rateRepository.deleteFieldRate(id);
    }

    @Override
    public void deleteWells(Long id) {

    }


}
