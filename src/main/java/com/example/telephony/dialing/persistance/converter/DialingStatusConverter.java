package com.example.telephony.dialing.persistance.converter;

import com.example.telephony.dialing.persistance.enums.DialingStatus;

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
