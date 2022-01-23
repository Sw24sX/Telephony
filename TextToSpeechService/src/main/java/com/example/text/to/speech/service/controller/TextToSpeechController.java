package com.example.text.to.speech.service.controller;

import com.example.text.to.speech.service.dto.CreatedAudioFileResponse;
import com.example.text.to.speech.service.dto.SpeechTextRequest;
import com.example.text.to.speech.service.service.TextToSpeechService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController("text-to-speech")
public class TextToSpeechController {
    private final TextToSpeechService textToSpeechService;

    public TextToSpeechController(TextToSpeechService textToSpeechService) {
        this.textToSpeechService = textToSpeechService;
    }

    @PostMapping()
    public CreatedAudioFileResponse textToSpeech(@RequestBody SpeechTextRequest speechTextRequest) throws IOException {
        return textToSpeechService.textToSpeech(speechTextRequest);
    }
}
