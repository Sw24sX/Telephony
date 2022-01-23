package com.example.text.to.speech.service.dto;

import com.example.text.to.speech.service.enums.Voice;
import lombok.Data;

@Data
public class SpeechTextRequest {
    private String text;
    private Voice voice;
}
