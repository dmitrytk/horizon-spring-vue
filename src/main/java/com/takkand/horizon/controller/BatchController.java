package com.takkand.horizon.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.takkand.horizon.domain.Field;
import com.takkand.horizon.domain.Mer;
import com.takkand.horizon.domain.Well;
import com.takkand.horizon.repository.FieldRepository;
import com.takkand.horizon.repository.InclinometryRepository;
import com.takkand.horizon.repository.MerRepository;
import com.takkand.horizon.repository.WellRepository;
import com.takkand.horizon.util.JsonNodeValidator;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
    private final MerRepository merRepository;
    private final JdbcTemplate jdbcTemplate;

    public BatchController(WellRepository wellRepository, FieldRepository fieldRepository, InclinometryRepository inclinometryRepository, MerRepository merRepository, JdbcTemplate jdbcTemplate) {
        this.wellRepository = wellRepository;
        this.fieldRepository = fieldRepository;
        this.inclinometryRepository = inclinometryRepository;
        this.merRepository = merRepository;
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
    ResponseEntity<String> loadInclinometry(@RequestBody JsonNode payload) {
        if (!JsonNodeValidator.validateInclinometryJson(payload))
            return new ResponseEntity<>("Invalid data", HttpStatus.INTERNAL_SERVER_ERROR);

        // Get field id
        Long fieldId;
        try {
            fieldId = fieldRepository.findByName(payload.get("field").asText()).getId();
        } catch (NullPointerException e) {
            System.out.println(e.toString());
            return new ResponseEntity<>("Field not found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Well names to delete old inclinometry
        List<String> wellNames = new ArrayList<>();

        // Inclinometry data
        List<Object[]> inclinometry = new ArrayList<>();
        payload.get("inclinometry").elements().forEachRemaining(node -> {
            if (JsonNodeValidator.validateInclinometryRow(node)) {
                String wellName = node.get("well").isTextual() ? node.get("well").asText() : null;
                Double md = node.get("md").isNumber() ? node.get("md").asDouble() : null;
                Double azi = node.get("azi").isNumber() ? node.get("azi").asDouble() : null;
                Double inc = node.get("inc").isNumber() ? node.get("inc").asDouble() : null;
                wellNames.add(wellName);
                System.out.println(Arrays.toString(new Object[]{wellName, fieldId, md, inc, azi}));
                inclinometry.add(new Object[]{wellName, fieldId, md, inc, azi});
            }
        });
        try {
            // Delete old inclinometry
            System.out.println(wellNames);
            inclinometryRepository.deleteInclinometryByWellNames(fieldId, wellNames);

            // Insert new inclinometry
            jdbcTemplate.batchUpdate("INSERT INTO inclinometry(well_id, md, inc, azi)\n" +
                    "VALUES\n" +
                    "((SELECT id FROM wells w WHERE w.name=? and w.field_id=?),?,?,?)", inclinometry);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Well does not exists\n" + e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(inclinometry.size() + " inclinometry records loaded", HttpStatus.OK);
    }


    @PostMapping("/mer")
    ResponseEntity<String> loadMer(@RequestBody JsonNode payload) {
        if (!JsonNodeValidator.validateMerJson(payload)) {
            System.out.println("Error");
            return new ResponseEntity<>("Invalid data", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Get field id
        Long fieldId;
        try {
            fieldId = fieldRepository.findByName(payload.get("field").asText()).getId();
        } catch (NullPointerException e) {
            System.out.println(e.toString());
            return new ResponseEntity<>("Field not found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Well names to delete old inclinometry
        List<String> wellNames = new ArrayList<>();

        // Inclinometry data
        List<Object[]> mer = new ArrayList<>();
        List<Mer> merObjects = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        ObjectMapper objectMapper = new ObjectMapper();
        payload.get("mer").elements().forEachRemaining(node -> {
            if (JsonNodeValidator.validateMerRow(node)) {
                try {

                    /// !!! Use objectMapper instead of manual mapping !!!
                    Mer m = objectMapper.treeToValue(node, Mer.class);
                    merObjects.add(m);
                    ///
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                String wellName = node.get("well").isTextual() ? node.get("well").asText() : null;
                LocalDate date = node.get("date").isTextual()
                        ? LocalDate.parse(node.get("date").asText(), formatter)
                        : null;
                String status = node.has("status") && node.get("status").isTextual()
                        ? node.get("status").asText() : null;
                Double rate = node.has("rate") && node.get("rate").isNumber()
                        ? node.get("rate").asDouble() : null;
                Double production = node.has("production") && node.get("production").isNumber()
                        ? node.get("production").asDouble() : null;
                Double work_days = node.has("work_days") && node.get("work_days").isNumber()
                        ? node.get("work_days").asDouble() : null;
                wellNames.add(wellName);
                mer.add(new Object[]{wellName, fieldId, date, status, rate, production, work_days});

            }
        });
        try {
            // Delete old inclinometry
            merRepository.deleteMerByWellNames(fieldId, wellNames);

            // Insert new inclinometry
            jdbcTemplate.batchUpdate("INSERT INTO mer(well_id, date, status, rate," +
                    "production, work_days) VALUES\n" +
                    "((SELECT id FROM wells w WHERE w.name=? and w.field_id=?),?,?,?,?,?)", mer);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Well does not exists\n" + e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(mer.size() + " mer records loaded\n" + merObjects, HttpStatus.OK);
    }

}
