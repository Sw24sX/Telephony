package com.example.text.to.speech.service.controller;

import com.example.text.to.speech.service.dto.CreatedAudioFileResponse;
import com.example.text.to.speech.service.dto.SpeechTextRequest;
import com.example.text.to.speech.service.service.TextToSpeechService;
import ie.corballis.sox.WrongParametersException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController()
@RequestMapping("tts")
public class TextToSpeechController {
    private final TextToSpeechService textToSpeechService;

    public TextToSpeechController(TextToSpeechService textToSpeechService) {
        this.textToSpeechService = textToSpeechService;
    }

    @PostMapping("create")
    public CreatedAudioFileResponse textToSpeech(@RequestBody SpeechTextRequest speechTextRequest) throws IOException, WrongParametersException {
        return textToSpeechService.textToSpeech(speechTextRequest);
    }

    @PostMapping("create/list")
    public List<CreatedAudioFileResponse> textToSpeechList(@RequestBody List<SpeechTextRequest> request) {
        return textToSpeechService.textToSpeechList(request);
    }
}
