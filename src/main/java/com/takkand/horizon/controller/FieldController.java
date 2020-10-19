package com.takkand.horizon.controller;

import com.takkand.horizon.domain.Field;
import com.takkand.horizon.domain.Well;
import com.takkand.horizon.exception.ResourceNotFoundException;
import com.takkand.horizon.repository.FieldRepository;
import com.takkand.horizon.repository.InclinometryRepository;
import com.takkand.horizon.repository.MerRepository;
import com.takkand.horizon.repository.RateRepository;
import com.takkand.horizon.util.QueryMatcher;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/fields")
public class FieldController {

    private final FieldRepository fieldRepository;
    private final InclinometryRepository inclinometryRepository;
    private final MerRepository merRepository;
    private final RateRepository rateRepository;

    public FieldController(FieldRepository fieldRepository, InclinometryRepository inclinometryRepository, MerRepository merRepository, RateRepository rateRepository) {
        this.fieldRepository = fieldRepository;
        this.inclinometryRepository = inclinometryRepository;
        this.merRepository = merRepository;
        this.rateRepository = rateRepository;
    }

    @GetMapping
    List<Field> findAll() {
        return fieldRepository.findAll();
    }

    @PostMapping
    Field createField(@RequestBody Field newField) {
        return fieldRepository.save(newField);
    }

    @GetMapping("/{id}")
    Field findById(@PathVariable Long id) {
        return fieldRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(id)));
    }

    @GetMapping("/{id}/wells")
    List<Well> findFieldWells(@PathVariable Long id) {
        return fieldRepository.findById(id)
                .map(Field::getWells)
                .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(id)));
    }


    @GetMapping("/{id}/inclinometry")
    List<Map<String, Object>> findFieldInclinometryWithWellNames(@PathVariable Long id) {
        List<Object[]> set = inclinometryRepository.findFieldInclinometryWithWellNames(id);
        return QueryMatcher.inclinometryMatcher(set);
    }

    @GetMapping("/{id}/mer")
    List<Map<String, Object>> findFieldMerWithWellNames(@PathVariable Long id) {
        List<Object[]> resultSet = merRepository.findFieldMerWithWellNames(id);
        return QueryMatcher.merMatcher(resultSet);
    }

    @GetMapping("/{id}/rates")
    List<Map<String, Object>> findFieldRatesWithWellNames(@PathVariable Long id) {
        List<Object[]> resultSet = rateRepository.findFieldRatesWithWellNames(id);
        return QueryMatcher.ratesMatcher(resultSet);
    }
}
