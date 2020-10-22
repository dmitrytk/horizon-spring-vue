package com.takkand.horizon.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.takkand.horizon.domain.Field;
import com.takkand.horizon.domain.Well;
import com.takkand.horizon.domain.load.MerLoad;
import com.takkand.horizon.domain.load.setter.MerBatchSetter;
import com.takkand.horizon.repository.FieldRepository;
import com.takkand.horizon.repository.InclinometryRepository;
import com.takkand.horizon.repository.MerRepository;
import com.takkand.horizon.repository.WellRepository;
import com.takkand.horizon.sql.Queries;
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
    ResponseEntity<String> loadNewMer(@RequestBody MerLoad payload) {
        if (!payload.isValid())
            return new ResponseEntity<>("Invalid data", HttpStatus.INTERNAL_SERVER_ERROR);


        // Get field id
        Long fieldId;
        try {
            fieldId = fieldRepository.findByName(payload.getField()).getId();
        } catch (NullPointerException e) {
            return new ResponseEntity<>("Field not found", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Filter invalid mer data
        List<MerLoad.MerData> mer = payload.getMer().stream()
                .filter(MerLoad.MerData::isValid).collect(Collectors.toList());

        try {
            // Insert new mer
            int[] updateCounts = jdbcTemplate.batchUpdate(Queries.merLoadQuery, new MerBatchSetter(fieldId, mer));

            return new ResponseEntity<>(updateCounts.length + " mer records loaded\n", HttpStatus.OK);

        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Database error:\n" + e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
