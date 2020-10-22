package com.takkand.horizon.controller;

import com.takkand.horizon.domain.*;
import com.takkand.horizon.exception.ResourceNotFoundException;
import com.takkand.horizon.repository.WellRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/wells")
public class WellController {

    private final WellRepository wellRepository;

    public WellController(WellRepository wellRepository) {
        this.wellRepository = wellRepository;
    }


    // BASIC
    @GetMapping
    List<Well> all() {
        return wellRepository.findAll();
    }

    @GetMapping("/{id}")
    Well one(@PathVariable Long id) {
        return wellRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @PostMapping
    Well create(@RequestBody Well newWell) {
        return wellRepository.save(newWell);
    }

    @PutMapping("/{id}")
    Well update(@RequestBody Well newWell, @PathVariable Long id) {
        return wellRepository.findById(id)
                .map(well -> {
                    well.update(newWell);
                    return wellRepository.save(well);
                })
                .orElseGet(() -> {
                    newWell.setId(id);
                    return wellRepository.save(newWell);
                });
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        wellRepository.deleteById(id);
    }


    // GET CHILD OBJECTS
    @GetMapping("/{id}/inclinometry")
    List<Inclinometry> getInclinometry(@PathVariable Long id) {
        return wellRepository.findById(id)
                .map(Well::getInclinometry)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @GetMapping("/{id}/mer")
    List<Mer> getMer(@PathVariable Long id) {
        return wellRepository.findById(id)
                .map(Well::getMer)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @GetMapping("/{id}/rates")
    List<Rate> getRates(@PathVariable Long id) {
        return wellRepository.findById(id)
                .map(Well::getRates)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @GetMapping("/{id}/zones")
    List<Zone> getZones(@PathVariable Long id) {
        return wellRepository.findById(id)
                .map(Well::getZones)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    // DELETE CHILD OBJECTS
    @DeleteMapping("/{id}/inclinometry")
    void deleteInclinometry(@PathVariable Long id) {
        wellRepository.findById(id)
                .map(well -> {
                    well.getInclinometry().clear();
                    return wellRepository.save(well);
                })
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @DeleteMapping("/{id}/mer")
    void deleteMer(@PathVariable Long id) {
        wellRepository.findById(id)
                .map(well -> {
                    well.getMer().clear();
                    return wellRepository.save(well);
                })
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @DeleteMapping("/{id}/rates")
    void deleteRates(@PathVariable Long id) {
        wellRepository.findById(id)
                .map(well -> {
                    well.getRates().clear();
                    return wellRepository.save(well);
                })
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @DeleteMapping("/{id}/zones")
    void deleteZones(@PathVariable Long id) {
        wellRepository.findById(id)
                .map(well -> {
                    well.getZones().clear();
                    return wellRepository.save(well);
                })
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }
}
