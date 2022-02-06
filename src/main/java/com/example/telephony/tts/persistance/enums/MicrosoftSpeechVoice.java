package com.example.telephony.tts.persistance.enums;

/**
 * Microsoft desktop voices
 */
public enum MicrosoftSpeechVoice {
    IRINA("Microsoft Irina Desktop", "Русский", (short) 1),
    ZIRA("Microsoft Zira Desktop", "English", (short) 2);

    private final String value;
    private final String language;
    private final short code;

    MicrosoftSpeechVoice(String value, String language, short code) {
        this.value = value;
        this.language = language;
        this.code = code;
    }

    public short getCode() {
        return code;
    }

    public String getLanguage() {
        return language;
    }

    public String getValue() {
        return value;
    }
}
