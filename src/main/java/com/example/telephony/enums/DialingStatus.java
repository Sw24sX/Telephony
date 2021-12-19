package com.example.telephony.enums;

import com.example.telephony.enums.messages.ExceptionMessage;
import com.example.telephony.exception.TelephonyException;

import java.util.Arrays;
import java.util.Objects;

public enum DialingStatus {
    RUN((short) 1, "Запущен"),
    SCHEDULED((short) 2, "Отложен"),
    DONE((short) 3, "Готов");

    private final short code;
    private final String message;

    DialingStatus(short code, String message) {
        this.code = code;
        this.message = message;
    }

    public short getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static DialingStatus getByCode(Short code) {
        return Arrays.stream(values())
                .filter(value -> Objects.equals(value.code, code))
                .findFirst()
                .orElseThrow(() ->
                        new TelephonyException(String.format(ExceptionMessage.WRONG_ENUM_CODE.getMessage(), code)));
    }
}
