package com.example.telephony.dialing.persistance.enums;

import com.example.telephony.enums.exception.messages.ExceptionMessage;
import com.example.telephony.exception.TelephonyException;

import java.util.Arrays;
import java.util.Objects;

public enum DialCallerStatus {
    CORRECT((short) 1, "Успешно завершен"),
    HAVEN_NOT_REACHED((short) 2, "Не дозвонились"),
    SCENARIO_NOT_END((short) 3, "Сценарий не завершен"),
    IN_PROGRESS((short) 4, "В процессе");

    private final short code;
    private final String message;

    DialCallerStatus(short code, String message) {
        this.code = code;
        this.message = message;
    }

    public short getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static DialCallerStatus getByCode(Short code) {
        return Arrays.stream(values())
                .filter(value -> Objects.equals(value.code, code))
                .findFirst()
                .orElseThrow(() ->
                        new TelephonyException(String.format(ExceptionMessage.WRONG_ENUM_CODE.getMessage(), code)));
    }
}
