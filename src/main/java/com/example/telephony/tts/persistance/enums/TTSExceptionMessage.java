package com.example.telephony.tts.persistance.enums;

public enum TTSExceptionMessage {
    NOT_CORRECT_NAME_ENGINE("Имя движка для озвучивания введено неправильно");

    private final String message;

    TTSExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
