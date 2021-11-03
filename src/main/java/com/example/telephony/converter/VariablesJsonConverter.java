package com.example.telephony.converter;

import com.example.telephony.exception.TelephonyException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import springfox.documentation.spring.web.json.Json;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.Map;

@Converter
public class VariablesJsonConverter implements AttributeConverter<Map<String, String>, String> {

    @Override
    public String convertToDatabaseColumn(Map<String, String> variables) {
        String customerInfoJson = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            customerInfoJson = objectMapper.writeValueAsString(variables);
        } catch (final JsonProcessingException e) {
            throw new TelephonyException("Something wrong");
        }

        return customerInfoJson;
    }

    @Override
    public Map<String, String> convertToEntityAttribute(String json) {
        Map<String, String> customerInfo = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            customerInfo = objectMapper.readValue(json, Map.class);
        } catch (final IOException e) {
            throw new TelephonyException("Something wrong");
        }

        return customerInfo;
    }
}
