package com.example.telephony.converter;


import com.example.telephony.enums.DialCallerStatus;

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
