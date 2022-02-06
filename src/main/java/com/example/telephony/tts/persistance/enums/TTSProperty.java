package com.example.telephony.tts.persistance.enums;

/**
 * Properties for tts
 */
public enum TTSProperty {
    TTS_TEMP_FILE("file.temp.path"),
    TTS_RESULT_FILE("file.generated.path"),
    TTS_RESULT_URL("file.generated.url"),
    TTS_RESULT_PATTERN("file.generated.pattern"),
    SOX_PATH("sox.path"),
    SOX_ENV_PATH("sox.env.path"),
    SERVER_ADDRESS("server.address"),
    SERVER_PORT("server.port"),
    TTS_ENGINE("tts.engine");

    private final String property;

    TTSProperty(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }
}
