package com.example.text.to.speech.service.enums;

/**
 * Enum with name properties from application.properties
 */
public enum CustomApplicationProperty {
    TTS_TEMP_FILE("tts.temp.file"),
    TTS_RESULT_FILE("tts.result.file"),
    TTS_RESULT_URL("tts.result.url"),
    TTS_RESULT_PATTERN("tts.result.pattern"),
    SOX_PATH("sox.path"),
    SOX_ENV_PATH("sox.env.path");

    private final String name;

    CustomApplicationProperty(String name) {
        this.name = name;
    }

    /**
     * Property name in application.properties
     */
    public String getName() {
        return name;
    }
}
