package com.example.telephony.tts.persistance.enums;

import com.example.telephony.tts.exception.TextToSpeechException;

import java.util.Arrays;

/**
 * Values for property tts.engine
 */
public enum EngineName {
    GOOGLE("google"),
    MICROSOFT("microsoft");

    private final String name;

    EngineName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static EngineName getByName(String name) {
        return Arrays.stream(values())
                .filter(value -> value.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new TextToSpeechException(TTSExceptionMessage.NOT_CORRECT_NAME_ENGINE));
    }
}
