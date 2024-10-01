package com.companyname.models.playerserviceapi;

import io.qameta.allure.internal.shadowed.jackson.core.type.TypeReference;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;

import java.util.Map;

public abstract class BaseDTO {
    //TODO: move method to DTO base class
    public Map<String, String> convertToMap() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(this, new TypeReference<Map<String, String>>() {
        });
    }
}
