package com.telekom.whatsapp.webhook.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HashMapConverter implements AttributeConverter<Map<String, Object>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Object> mappedValues) {

        String jsonObject = null;
        try {
            jsonObject = objectMapper.writeValueAsString(mappedValues);

        } catch (JsonProcessingException e) {
            System.err.println("Error when converting map to string: " + e);
        }
        return jsonObject;
    }

    @Override
    @SuppressWarnings("all")
    public Map<String, Object> convertToEntityAttribute(String jsonObject) {
        Map<String, Object> mappedValues = new HashMap<String, Object>();
        
        try {
            mappedValues = objectMapper.readValue(jsonObject, Map.class);

        } catch ( IOException e) {
            System.err.println(e);
        }
        return mappedValues;
    }
}