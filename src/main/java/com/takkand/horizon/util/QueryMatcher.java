package com.takkand.horizon.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryMatcher {

    // Columns in result set from native queries
    private static final String[] inclinometryMatcherColumns = {"well", "md", "azi", "inc"};
    private static final String[] merMatcherColumns = {"well", "date", "status", "rate", "production", "work_days"};
    private static final String[] ratesMatcherColumns = {"well", "date", "rate", "dynamic", "static", "pressure"};

    // Map inclinometry result set with well names
    public static List<Map<String, Object>> inclinometryMatcher(List<Object[]> resultSet) {
        return getMapping(resultSet, inclinometryMatcherColumns);
    }

    // Map Mer result set with well names
    public static List<Map<String, Object>> merMatcher(List<Object[]> resultSet) {
        return getMapping(resultSet, merMatcherColumns);
    }

    // Map Rates result set with well names
    public static List<Map<String, Object>> ratesMatcher(List<Object[]> resultSet) {
        return getMapping(resultSet, ratesMatcherColumns);
    }


    // Basic mapping method
    private static List<Map<String, Object>> getMapping(List<Object[]> resultSet, String[] mappingColumns) {
        return resultSet.stream().map(el -> {
            Map<String, Object> objectMap = new HashMap<>();
            for (int i = 0; i < el.length; i++) {
                objectMap.put(mappingColumns[i], el[i]);
            }
            return objectMap;
        }).collect(Collectors.toList());
    }
}
