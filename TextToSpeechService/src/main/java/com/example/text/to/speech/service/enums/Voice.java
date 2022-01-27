package com.example.text.to.speech.service.enums;

import com.google.cloud.texttospeech.v1.SsmlVoiceGender;

public enum Voice {
    RUSSIAN("ru-RU", SsmlVoiceGender.NEUTRAL),
    AMERICAN("en-US", SsmlVoiceGender.NEUTRAL);

    private final String languageCode;
    private final SsmlVoiceGender voiceGender;


    Voice(String languageCode, SsmlVoiceGender voiceGender) {
        this.languageCode = languageCode;
        this.voiceGender = voiceGender;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public SsmlVoiceGender getVoiceGender() {
        return voiceGender;
    }
}

