package com.example.telephony.enums;

import com.example.telephony.enums.exception.messages.ExceptionMessage;
import com.example.telephony.exception.TelephonyException;

import java.util.Arrays;
import java.util.Objects;


public enum ScenarioNodeTypes {
    START("start", (short) 1),
    FINISH("finish", (short) 2),
    REPLICA("replica", (short) 3);

    private final String extraName;
    private final short code;

    ScenarioNodeTypes(String extraName, short code) {
        this.extraName = extraName;
        this.code = code;
    }

    public String getExtraName() {
        return extraName;
    }

    public short getCode() {
        return code;
    }

    public static ScenarioNodeTypes getByCode(short code) {
        return Arrays.stream(values())
                .filter(value -> Objects.equals(value.code, code))
                .findFirst()
                .orElseThrow(() ->
                        new TelephonyException(String.format(ExceptionMessage.WRONG_ENUM_CODE.getMessage(), code)));
    }

    public static ScenarioNodeTypes getByExtraName(String extraName) {
        return Arrays.stream(values())
                .filter(value -> Objects.equals(value.extraName, extraName))
                .findFirst()
                .orElseThrow(() ->
                        new TelephonyException(String.format(ExceptionMessage.WRONG_ENUM_CODE.getMessage(), extraName)));
    }
}
