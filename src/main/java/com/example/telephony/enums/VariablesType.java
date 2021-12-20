package com.example.telephony.enums;

import com.example.telephony.enums.messages.ExceptionMessage;
import com.example.telephony.exception.TelephonyException;

import java.util.Arrays;
import java.util.Objects;

public enum VariablesType {
    INDEFINITE("Неопределенный", (short) 0),
    STRING("Строка", (short) 1),
    NUMBER("Число", (short) 2),
    DATA("Дата", (short) 3),
    TIME("Время", (short) 4),
    BOOLEAN("Логический", (short) 5);

    private final String description;
    private final Short code;

    VariablesType(String description, Short code) {
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public Short getCode() {
        return code;
    }

    public static VariablesType getByCode(Short code) {
        return Arrays.stream(values())
                .filter(value -> Objects.equals(value.code, code))
                .findFirst()
                .orElseThrow(() ->
                        new TelephonyException(String.format(ExceptionMessage.WRONG_ENUM_CODE.getMessage(), code)));
    }
}
