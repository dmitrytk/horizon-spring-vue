package com.takkand.horizon.controller;

import com.takkand.horizon.domain.Field;
import com.takkand.horizon.domain.FieldCoordinates;
import com.takkand.horizon.domain.Well;
import com.takkand.horizon.domain.view.IncView;
import com.takkand.horizon.domain.view.MerView;
import com.takkand.horizon.domain.view.RateView;
import com.takkand.horizon.domain.view.ZoneView;
import com.takkand.horizon.exception.ResourceNotFoundException;
import com.takkand.horizon.repository.*;
import com.takkand.horizon.sql.Queries;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


@RestController
@RequestMapping("/api/fields")
public class FieldController {

    private final EntityManager manager;
    private final FieldRepository fieldRepository;
    private final InclinometryRepository inclinometryRepository;
    private final MerRepository merRepository;
    private final RateRepository rateRepository;
    private final FieldCoordinatesRepository fieldCoordinatesRepository;

    public FieldController(EntityManager manager, FieldRepository fieldRepository, InclinometryRepository inclinometryRepository, MerRepository merRepository, RateRepository rateRepository, FieldCoordinatesRepository fieldCoordinatesRepository) {
        this.manager = manager;
        this.fieldRepository = fieldRepository;
        this.inclinometryRepository = inclinometryRepository;
        this.merRepository = merRepository;
        this.rateRepository = rateRepository;
        this.fieldCoordinatesRepository = fieldCoordinatesRepository;
    }


    // BASIC
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

    // GET CHILD OBJECTS
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
    List<IncView> getInclinometryView(@PathVariable Long id) {
        Query query = manager.createNativeQuery(Queries.FIELD_INCLINOMETRY_VIEW, IncView.class);
        query.setParameter("fieldId", id);
        return query.getResultList();
    }

    @GetMapping("/{id}/mer")
    List<MerView> getMerView(@PathVariable Long id) {
        Query query = manager.createNativeQuery(Queries.FIELD_MER_VIEW, MerView.class);
        query.setParameter("fieldId", id);
        return query.getResultList();
    }

    @GetMapping("/{id}/rates")
    List<RateView> getRatesView(@PathVariable Long id) {
        Query query = manager.createNativeQuery(Queries.FIELD_RATES_VIEW, RateView.class);
        query.setParameter("fieldId", id);
        return query.getResultList();
    }

    @GetMapping("/{id}/zones")
    List<ZoneView> getZonesView(@PathVariable Long id) {
        Query query = manager.createNativeQuery(Queries.FIELD_ZONES_VIEW, ZoneView.class);
        query.setParameter("fieldId", id);
        return query.getResultList();
    }


    // DELETE CHILD OBJECTS
    @DeleteMapping("/{id}/coordinates")
    void deleteCoordinates(@PathVariable Long id) {
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
