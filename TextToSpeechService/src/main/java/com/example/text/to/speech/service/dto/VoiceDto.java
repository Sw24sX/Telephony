package com.example.text.to.speech.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Dto for endpoint for get list voices
 */
@Data
@AllArgsConstructor
public class VoiceDto {
    /**
     * Name in enum
     */
    private String name;

    /**
     * Language code for Google tts
     */
    private String languageCode;
}
