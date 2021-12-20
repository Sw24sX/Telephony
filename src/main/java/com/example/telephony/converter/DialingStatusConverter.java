package com.example.telephony.converter;

import com.example.telephony.enums.DialingStatus;

import javax.persistence.AttributeConverter;

public class DialingStatusConverter implements AttributeConverter<DialingStatus, Short> {
    @Override
    public Short convertToDatabaseColumn(DialingStatus dialingStatus) {
        return dialingStatus == null ? DialingStatus.DONE.getCode() : dialingStatus.getCode();
    }

    @Override
    public DialingStatus convertToEntityAttribute(Short code) {
        return DialingStatus.getByCode(code);
    }
}
