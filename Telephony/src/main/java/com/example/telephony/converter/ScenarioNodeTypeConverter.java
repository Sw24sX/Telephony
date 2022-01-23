package com.example.telephony.converter;

import com.example.telephony.enums.ScenarioNodeTypes;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ScenarioNodeTypeConverter implements AttributeConverter<ScenarioNodeTypes, Short> {
    @Override
    public Short convertToDatabaseColumn(ScenarioNodeTypes scenarioNodeTypes) {
        return scenarioNodeTypes == null ? ScenarioNodeTypes.REPLICA.getCode() : scenarioNodeTypes.getCode();
    }

    @Override
    public ScenarioNodeTypes convertToEntityAttribute(Short code) {
        return ScenarioNodeTypes.getByCode(code);
    }
}
