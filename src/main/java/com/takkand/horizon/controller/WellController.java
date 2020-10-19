package com.takkand.horizon.controller;

import com.takkand.horizon.domain.Inclinometry;
import com.takkand.horizon.domain.Mer;
import com.takkand.horizon.domain.Rate;
import com.takkand.horizon.domain.Well;
import com.takkand.horizon.exception.ResourceNotFoundException;
import com.takkand.horizon.repository.WellRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/wells")
public class WellController {

    private final WellRepository wellRepository;

    public WellController(WellRepository wellRepository) {
        this.wellRepository = wellRepository;
    }

    @GetMapping
    List<Well> findAll() {
        return wellRepository.findAll();
    }

    @GetMapping("/{id}")
    Well findById(@PathVariable Long id) {
        return wellRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @GetMapping("/{id}/inclinometry")
    List<Inclinometry> findWellInclinometry(@PathVariable Long id) {
        return wellRepository.findById(id)
                .map(Well::getInclinometry)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @GetMapping("/{id}/mer")
    List<Mer> findWellMer(@PathVariable Long id) {
        return wellRepository.findById(id)
                .map(Well::getMer)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @GetMapping("/{id}/rates")
    List<Rate> findWellRates(@PathVariable Long id) {
        return wellRepository.findById(id)
                .map(Well::getRates)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }
}
