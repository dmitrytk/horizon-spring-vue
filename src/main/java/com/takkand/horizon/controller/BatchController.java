package com.takkand.horizon.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.takkand.horizon.domain.Field;
import com.takkand.horizon.domain.Well;
import com.takkand.horizon.repository.FieldRepository;
import com.takkand.horizon.repository.WellRepository;
import com.takkand.horizon.util.RowValidator;
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
    private final JdbcTemplate jdbcTemplate;

    public BatchController(WellRepository wellRepository, FieldRepository fieldRepository, JdbcTemplate jdbcTemplate) {
        this.wellRepository = wellRepository;
        this.fieldRepository = fieldRepository;
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
        Field field = fieldRepository.findByName(payload.get("field").asText());
        List<Object[]> inclinometry = new ArrayList<>();
        payload.get("inclinometry").elements().forEachRemaining(node -> {
            if (RowValidator.validateInclinometryRow(node)) {
                Integer well_id = node.get("well_id").isNumber() ? node.get("well_id").asInt() : null;
                Double md = node.get("md").isNumber() ? node.get("md").asDouble() : null;
                Double azi = node.get("azi").isNumber() ? node.get("azi").asDouble() : null;
                Double inc = node.get("inc").isNumber() ? node.get("inc").asDouble() : null;
                System.out.println(Arrays.toString(new Object[]{well_id, md, inc, azi}));
                inclinometry.add(new Object[]{well_id, md, inc, azi});
            }
        });
        jdbcTemplate.batchUpdate("INSERT INTO inclinometry(well_id, md, inc, azi) VALUES (?,?,?,?)", inclinometry);
        return new ResponseEntity<>("Inclinometry loaded", HttpStatus.OK);
    }


}
