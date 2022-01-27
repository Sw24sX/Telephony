package com.example.text.to.speech.service.enums;

import com.google.cloud.texttospeech.v1.SsmlVoiceGender;

/**
 * Enum with used voices in Google tts
 */
public enum Voice {
    RUSSIAN("ru-RU", SsmlVoiceGender.NEUTRAL),
    AMERICAN("en-US", SsmlVoiceGender.NEUTRAL);

    private final String languageCode;
    private final SsmlVoiceGender voiceGender;

    Voice(String languageCode, SsmlVoiceGender voiceGender) {
        this.languageCode = languageCode;
        this.voiceGender = voiceGender;
    }

    /**
     * Language code
     * @return Language code
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * Voice gender
     * @return Voice gender
     */
    public SsmlVoiceGender getVoiceGender() {
        return voiceGender;
    }
}

