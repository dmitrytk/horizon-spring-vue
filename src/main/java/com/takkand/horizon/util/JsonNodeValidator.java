package com.takkand.horizon.util;

import com.fasterxml.jackson.databind.JsonNode;

public class JsonNodeValidator {
    private static final String dateRegex = "^ *\\d{2}\\.\\d{2}\\.\\d{4} *$";

    public static boolean validateInclinometryJson(JsonNode node) {
        return node.has("field") && node.get("field").isTextual()
                && node.has("inclinometry");
    }

    public static boolean validateMerJson(JsonNode node) {
        return node.has("field") && node.get("field").isTextual()
                && node.has("mer") && node.get("mer").isArray();
    }

    public static boolean validateInclinometryRow(JsonNode node) {
        return node.has("well")
                && node.has("md")
                && node.get("well").isTextual()
                && node.get("md").asDouble() > 0;
    }

    public static boolean validateMerRow(JsonNode node) {
        return node.has("well")
                && node.has("date")
                && node.get("well").isTextual()
                && node.get("date").asText().matches(dateRegex);
    }


}
