package com.example.telephony.converter;

import com.example.telephony.enums.VariablesType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class VariablesTypeCodeConverter implements AttributeConverter<VariablesType, Short> {
    @Override
    public Short convertToDatabaseColumn(VariablesType variablesType) {
        return variablesType == null ? VariablesType.STRING.getCode() : variablesType.getCode();
    }

    @Override
    public VariablesType convertToEntityAttribute(Short code) {
        return VariablesType.getByCode(code);
    }
}
