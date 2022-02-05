package com.example.dialerservice.enums;

public enum Properties {
    ASTERISK_NAME(""),
    ASTERISK_URL(""),
    ASTERISK_PASSWORD(""),
    ASTERISK_APP(""),
    ;

    private final String propertyName;

    Properties(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
