package com.example.text.to.speech.service.dto;

import com.example.text.to.speech.service.enums.Voice;
import lombok.Data;

/**
 * Request for create tts-file
 */
@Data
public class SpeechTextRequest {
    /**
     * Text for synthesis file
     */
    private String text;

    /**
     * Voice for synthesis file
     */
    private Voice voice;
}
