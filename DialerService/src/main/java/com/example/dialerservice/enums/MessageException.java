package com.example.dialerservice.enums;

public enum MessageException {
    ;

    private final String message;

    MessageException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
