package com.takkand.horizon.util;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

public class Loader {

    public static ResponseEntity<String> load(JdbcTemplate template, String query, BatchPreparedStatementSetter batchSetter) {

        try {
            // Insert new data
            int[] updateCounts = template.batchUpdate(query, batchSetter);

            return new ResponseEntity<>(updateCounts.length + " records loaded\n", HttpStatus.OK);

        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Database error:\n" + e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
