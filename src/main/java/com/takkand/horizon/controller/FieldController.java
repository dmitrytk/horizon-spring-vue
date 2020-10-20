package com.takkand.horizon.controller;

import com.takkand.horizon.domain.Field;
import com.takkand.horizon.domain.Well;
import com.takkand.horizon.exception.ResourceNotFoundException;
import com.takkand.horizon.repository.InclinometryRepository;
import com.takkand.horizon.repository.MerRepository;
import com.takkand.horizon.repository.RateRepository;
import com.takkand.horizon.service.FieldService;
import com.takkand.horizon.util.QueryMatcher;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/fields")
public class FieldController {

    private final FieldService fieldService;
    private final InclinometryRepository inclinometryRepository;
    private final MerRepository merRepository;
    private final RateRepository rateRepository;

    public FieldController(FieldService fieldService, InclinometryRepository inclinometryRepository, MerRepository merRepository, RateRepository rateRepository) {
        this.fieldService = fieldService;
        this.inclinometryRepository = inclinometryRepository;
        this.merRepository = merRepository;
        this.rateRepository = rateRepository;
    }


    @GetMapping
    List<Field> findAll() {
        return fieldService.findAll();
    }

    @GetMapping("/{id}")
    Field getOne(@PathVariable Long id) {
        return fieldService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @PostMapping
    Field createField(@RequestBody Field newField) {
        return fieldService.create(newField);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        fieldService.deleteById(id);
    }

    @PutMapping("/{id}")
    Field update(@RequestBody Field newField, @PathVariable Long id) {
        return fieldService.findById(id)
                .map(field -> {
                    field.update(newField);
                    return fieldService.update(field);
                })
                .orElseGet(() -> {
                    newField.setId(id);
                    return fieldService.create(newField);
                });

    }

    @GetMapping("/{id}/wells")
    List<Well> getWells(@PathVariable Long id) {
        return fieldService.findById(id)
                .map(Field::getWells)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }


    @GetMapping("/{id}/inclinometry")
    List<Map<String, Object>> getInclinometryView(@PathVariable Long id) {
        List<Object[]> set = inclinometryRepository.findFieldInclinometryWithWellNames(id);
        return QueryMatcher.inclinometryMatcher(set);
    }


    @GetMapping("/{id}/mer")
    List<Map<String, Object>> getMerView(@PathVariable Long id) {
        List<Object[]> resultSet = merRepository.findFieldMerWithWellNames(id);
        return QueryMatcher.merMatcher(resultSet);
    }


    @GetMapping("/{id}/rates")
    List<Map<String, Object>> getRatesView(@PathVariable Long id) {
        List<Object[]> resultSet = rateRepository.findFieldRatesWithWellNames(id);
        return QueryMatcher.ratesMatcher(resultSet);
    }

    // Delete orphan objects
    @DeleteMapping("/{id}/wells")
    void deleteWells(@PathVariable Long id) {
        fieldService.deleteWells(id);
    }

    @DeleteMapping("/{id}/mer")
    void deleteMer(@PathVariable Long id) {
        fieldService.deleteMer(id);
    }

    @DeleteMapping("/{id}/rates")
    void deleteRates(@PathVariable Long id) {
        fieldService.deleteRates(id);
    }

    @DeleteMapping("/{id}/inclinometry")
    void deleteInclinometry(@PathVariable Long id) {
        fieldService.deleteInclinometry(id);
    }
}
