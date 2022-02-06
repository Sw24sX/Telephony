package com.example.telephony.tts.persistance.enums;

import com.google.cloud.texttospeech.v1.SsmlVoiceGender;

/**
 * Google engine voices
 */
public enum GoogleSpeechVoice {
    RUSSIAN("ru-RU", SsmlVoiceGender.NEUTRAL),
    AMERICAN("en-US", SsmlVoiceGender.NEUTRAL);

    private final String languageCode;
    private final SsmlVoiceGender voiceGender;

    GoogleSpeechVoice(String languageCode, SsmlVoiceGender voiceGender) {
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
