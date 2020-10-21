package com.takkand.horizon.controller;

import com.takkand.horizon.domain.Field;
import com.takkand.horizon.domain.FieldCoordinates;
import com.takkand.horizon.domain.Well;
import com.takkand.horizon.exception.ResourceNotFoundException;
import com.takkand.horizon.repository.*;
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
    private final FieldCoordinatesRepository fieldCoordinatesRepository;

    public FieldController(FieldRepository fieldRepository, InclinometryRepository inclinometryRepository, MerRepository merRepository, RateRepository rateRepository, FieldCoordinatesRepository fieldCoordinatesRepository) {
        this.fieldRepository = fieldRepository;
        this.inclinometryRepository = inclinometryRepository;
        this.merRepository = merRepository;
        this.rateRepository = rateRepository;
        this.fieldCoordinatesRepository = fieldCoordinatesRepository;
    }


    @GetMapping
    List<Field> all() {
        return fieldRepository.findAll();
    }

    @GetMapping("/{id}")
    Field one(@PathVariable Long id) {
        return fieldRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @PostMapping
    Field create(@RequestBody Field newField) {
        return fieldRepository.save(newField);
    }

    @PutMapping("/{id}")
    Field update(@RequestBody Field newField, @PathVariable Long id) {
        return fieldRepository.findById(id)
                .map(field -> {
                    field.update(newField);
                    return fieldRepository.save(field);
                })
                .orElseGet(() -> {
                    newField.setId(id);
                    return fieldRepository.save(newField);
                });

    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        fieldRepository.deleteById(id);
    }

    @GetMapping("/{id}/wells")
    List<Well> getWells(@PathVariable Long id) {
        return fieldRepository.findById(id)
                .map(Field::getWells)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @GetMapping("/{id}/coordinates")
    List<FieldCoordinates> getCoordinates(@PathVariable Long id) {
        return fieldRepository.findById(id)
                .map(Field::getCoordinates)
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
    @DeleteMapping("/{id}/coordinates")
    void deleteCoordinatess(@PathVariable Long id) {
        fieldCoordinatesRepository.deleteFieldCoordinates(id);
    }

    @DeleteMapping("/{id}/wells")
    void deleteWells(@PathVariable Long id) {
        fieldRepository.deleteWells(id);
    }

    @DeleteMapping("/{id}/mer")
    void deleteMer(@PathVariable Long id) {
        merRepository.deleteFieldMer(id);
    }

    @DeleteMapping("/{id}/rates")
    void deleteRates(@PathVariable Long id) {
        rateRepository.deleteFieldRate(id);
    }

    @DeleteMapping("/{id}/inclinometry")
    void deleteInclinometry(@PathVariable Long id) {
        inclinometryRepository.deleteFieldInclinometry(id);
    }
}
