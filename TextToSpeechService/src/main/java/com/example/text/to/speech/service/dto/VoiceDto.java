package com.example.text.to.speech.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VoiceDto {
    private String name;
    private String languageCode;
}
