package com.takkand.horizon.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.takkand.horizon.domain.Field;
import com.takkand.horizon.domain.Well;
import com.takkand.horizon.domain.view.*;
import com.takkand.horizon.load.Payload;
import com.takkand.horizon.load.setter.InclinometryBatchSetter;
import com.takkand.horizon.load.setter.MerBatchSetter;
import com.takkand.horizon.load.setter.RateBatchSetter;
import com.takkand.horizon.load.setter.ZoneBatchSetter;
import com.takkand.horizon.repository.FieldRepository;
import com.takkand.horizon.repository.InclinometryRepository;
import com.takkand.horizon.repository.WellRepository;
import com.takkand.horizon.sql.Queries;
import com.takkand.horizon.util.Loader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public BatchController(WellRepository wellRepository, FieldRepository fieldRepository, InclinometryRepository inclinometryRepository, JdbcTemplate jdbcTemplate) {
        this.wellRepository = wellRepository;
        this.fieldRepository = fieldRepository;
        this.inclinometryRepository = inclinometryRepository;
        this.jdbcTemplate = jdbcTemplate;
    }


    @PostMapping("/wells")
    ResponseEntity<String> loadWells(@RequestBody JsonNode payload) {
        Field field = fieldRepository.findByName(payload.get("field").asText());
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
    ResponseEntity<String> loadInclinometry(@RequestBody Payload<InclinometryView> payload) {
        if (!payload.isValid())
            return new ResponseEntity<>("Invalid data", HttpStatus.INTERNAL_SERVER_ERROR);

        // Get field id
        Long fieldId;
        try {
            fieldId = fieldRepository.findByName(payload.getField()).getId();
        } catch (NullPointerException e) {
            System.out.println(e.toString());
            return new ResponseEntity<>("Field not found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Filter invalid data
        List<InclinometryView> data = payload.getValidData();

        // Well names to delete old inclinometry
        List<String> wellNames = data.stream()
                .map(View::getWellName).collect(Collectors.toList());

        // Delete old inclinometry
        inclinometryRepository.deleteInclinometryByWellNames(fieldId, wellNames);
        return Loader.load(jdbcTemplate, Queries.INCLINOMETRY_LOAD_QUERY, new InclinometryBatchSetter(fieldId, data));
    }


    @PostMapping("/mer")
    ResponseEntity<String> loadMer(@RequestBody Payload<MerView> payload) {
        if (!payload.isValid())
            return new ResponseEntity<>("Invalid data", HttpStatus.INTERNAL_SERVER_ERROR);

        // Get field id
        Long fieldId;
        try {
            fieldId = fieldRepository.findByName(payload.getField()).getId();
        } catch (NullPointerException e) {
            return new ResponseEntity<>("Field not found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Filter invalid data
        List<MerView> data = payload.getValidData();

        return Loader.load(jdbcTemplate, Queries.MER_LOAD_QUERY, new MerBatchSetter(fieldId, data));

    }


    @PostMapping("/rates")
    ResponseEntity<String> loadRates(@RequestBody Payload<RateView> payload) {
        if (!payload.isValid())
            return new ResponseEntity<>("Invalid data", HttpStatus.INTERNAL_SERVER_ERROR);

        // Get field id
        Long fieldId;
        try {
            fieldId = fieldRepository.findByName(payload.getField()).getId();
        } catch (NullPointerException e) {
            return new ResponseEntity<>("Field not found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Filter invalid data
        List<RateView> data = payload.getValidData();

        return Loader.load(jdbcTemplate, Queries.RATE_LOAD_QUERY, new RateBatchSetter(fieldId, data));


    }


    @PostMapping("/zones")
    ResponseEntity<String> loadZones(@RequestBody Payload<ZoneView> payload) {
        if (!payload.isValid())
            return new ResponseEntity<>("Invalid data", HttpStatus.INTERNAL_SERVER_ERROR);

        // Get field id
        Long fieldId;
        try {
            fieldId = fieldRepository.findByName(payload.getField()).getId();
        } catch (NullPointerException e) {
            return new ResponseEntity<>("Field not found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Filter invalid data
        List<ZoneView> data = payload.getValidData();

        return Loader.load(jdbcTemplate, Queries.ZONE_LOAD_QUERY, new ZoneBatchSetter(fieldId, data));


    }

}
