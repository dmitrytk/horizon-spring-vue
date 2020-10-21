package com.takkand.horizon.util;

import com.fasterxml.jackson.databind.JsonNode;

public class RowValidator {

    public static boolean validateInclinometryRow(JsonNode node) {
        return node.get("md").isNumber()
                && node.get("md").asDouble() > 0;
    }
}
