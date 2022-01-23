package com.example.text.to.speech.service.enums;

import com.google.cloud.texttospeech.v1.SsmlVoiceGender;

public enum Voice {
    RUSSIAN("Русский", "ru-RU", SsmlVoiceGender.NEUTRAL),
    AMERICAN("Американский", "en-US", SsmlVoiceGender.NEUTRAL);

    private final String name;
    private final String languageCode;
    private final SsmlVoiceGender voiceGender;


    Voice(String name, String languageCode, SsmlVoiceGender voiceGender) {
        this.name = name;
        this.languageCode = languageCode;
        this.voiceGender = voiceGender;
    }

    public String getName() {
        return name;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public SsmlVoiceGender getVoiceGender() {
        return voiceGender;
    }
}

