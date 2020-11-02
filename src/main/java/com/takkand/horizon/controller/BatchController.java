package com.takkand.horizon.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.takkand.horizon.domain.Field;
import com.takkand.horizon.domain.Well;
import com.takkand.horizon.domain.view.*;
import com.takkand.horizon.repository.FieldRepository;
import com.takkand.horizon.repository.InclinometryRepository;
import com.takkand.horizon.repository.WellRepository;
import com.takkand.horizon.service.BatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/batch")
public class BatchController {

    private final WellRepository wellRepository;
    private final FieldRepository fieldRepository;
    private final InclinometryRepository inclinometryRepository;
    private final JdbcTemplate jdbcTemplate;
    private final BatchService batchService;

    public BatchController(WellRepository wellRepository, FieldRepository fieldRepository, InclinometryRepository inclinometryRepository, JdbcTemplate jdbcTemplate, BatchService batchService) {
        this.wellRepository = wellRepository;
        this.fieldRepository = fieldRepository;
        this.inclinometryRepository = inclinometryRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.batchService = batchService;
    }


    @PostMapping("/wells")
    ResponseEntity<String> loadWells(@RequestBody JsonNode payload) {
        Field field = fieldRepository.findById(payload.get("fieldId").asLong()).get();
        Map<String, Well> wellMap = field.getWells().stream()
                .collect(Collectors.toMap(Well::getName, Function.identity()));
        List<Well> newWells = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        payload.get("wells").forEach(node -> {
            try {
                Well well = objectMapper.treeToValue(node, Well.class);
                System.out.println(well);
                Well matchWell = wellMap.getOrDefault(well.getName(), null);
                if (matchWell != null) {
                    matchWell.update(well);
                    newWells.add(matchWell);
                } else {
                    well.setField(field);
                    newWells.add(well);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        try {
            wellRepository.saveAll(newWells);
            return new ResponseEntity<>(newWells.size() + " wells updated", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/inclinometry")
    @Transactional
    ResponseEntity<String> loadInclinometry(@RequestBody Payload<InclinometryView> payload) {
        if (!payload.isValid())
            return new ResponseEntity<>("Invalid data", HttpStatus.INTERNAL_SERVER_ERROR);

        // Get field id
        Long fieldId = payload.getFieldId();

        // Filter invalid data
        List<InclinometryView> data = payload.getValidData();

        // Well names to delete old inclinometry
        List<String> wellNames = data.stream()
                .map(View::getWellName).collect(Collectors.toList());

        try {
            // Delete old inclinometry
            inclinometryRepository.deleteInclinometryByWellNames(fieldId, wellNames);
            // Load inclinometry
            int[] updateCounts = batchService.inclinometryImport(data, fieldId);
            return new ResponseEntity<>(updateCounts.length + " records loaded\n", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Database error:\n" + e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/mer")
    ResponseEntity<String> loadMer(@RequestBody Payload<MerView> payload) {
        if (!payload.isValid())
            return new ResponseEntity<>("Invalid data", HttpStatus.INTERNAL_SERVER_ERROR);

        // Get field id
        Long fieldId = payload.getFieldId();

        // Filter invalid data
        List<MerView> data = payload.getValidData();

        try {
            // Load mer
            int[] updateCounts = batchService.merImport(data, fieldId);
            return new ResponseEntity<>(updateCounts.length + " records loaded\n", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Database error:\n" + e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/rates")
    ResponseEntity<String> loadRates(@RequestBody Payload<RateView> payload) {
        if (!payload.isValid())
            return new ResponseEntity<>("Invalid data", HttpStatus.INTERNAL_SERVER_ERROR);

        // Get field id
        Long fieldId = payload.getFieldId();

        // Filter invalid data
        List<RateView> data = payload.getValidData();

        try {
            // Load mer
            int[] updateCounts = batchService.rateImport(data, fieldId);
            return new ResponseEntity<>(updateCounts.length + " records loaded\n", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Database error:\n" + e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PostMapping("/zones")
    ResponseEntity<String> loadZones(@RequestBody Payload<ZoneView> payload) {
        if (!payload.isValid())
            return new ResponseEntity<>("Invalid data", HttpStatus.INTERNAL_SERVER_ERROR);

        // Get field id
        Long fieldId = payload.getFieldId();

        // Filter invalid data
        List<ZoneView> data = payload.getValidData();

        try {
            // Load mer
            int[] updateCounts = batchService.zoneImport(data, fieldId);
            return new ResponseEntity<>(updateCounts.length + " records loaded\n", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Database error:\n" + e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

}
