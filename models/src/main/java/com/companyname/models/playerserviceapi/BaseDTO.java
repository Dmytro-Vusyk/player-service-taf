package com.companyname.models.playerserviceapi;

import io.qameta.allure.internal.shadowed.jackson.core.type.TypeReference;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;

import java.util.Map;

public abstract class BaseDTO {

    /**
     * Convert DTO to MAP of fields.
     *
     * @return the map
     */
    public Map<String, String> convertToMap() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(this, new TypeReference<Map<String, String>>() {
        });
    }
}
