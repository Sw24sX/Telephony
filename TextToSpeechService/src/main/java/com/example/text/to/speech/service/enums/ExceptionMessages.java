package com.example.text.to.speech.service.enums;

public enum ExceptionMessages {
    PROPERTY_NOT_FOUND("Свойство %s не найдено в application.properties");

    private final String message;

    ExceptionMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
