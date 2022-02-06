package com.example.telephony.dialing.persistance.converter;


import com.example.telephony.dialing.persistance.enums.DialCallerStatus;

import javax.persistence.AttributeConverter;

public class DialCallerStatusConverter implements AttributeConverter<DialCallerStatus, Short> {
    @Override
    public Short convertToDatabaseColumn(DialCallerStatus dialCallerStatus) {
        return dialCallerStatus == null ? DialCallerStatus.CORRECT.getCode() : dialCallerStatus.getCode();
    }

    @Override
    public DialCallerStatus convertToEntityAttribute(Short code) {
        return DialCallerStatus.getByCode(code);
    }
}
