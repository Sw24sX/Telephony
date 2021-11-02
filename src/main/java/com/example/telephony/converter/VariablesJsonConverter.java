package com.example.telephony.converter;

import springfox.documentation.spring.web.json.Json;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Map;

@Converter
public class VariablesJsonConverter implements AttributeConverter<Map<String, String>, Json> {

    @Override
    public Json convertToDatabaseColumn(Map<String, String> stringStringMap) {
        return null;
    }

    @Override
    public Map<String, String> convertToEntityAttribute(Json json) {
        return null;
    }
}
