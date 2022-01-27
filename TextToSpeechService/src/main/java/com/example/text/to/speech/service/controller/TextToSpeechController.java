package com.example.text.to.speech.service.controller;

import com.example.text.to.speech.service.dto.CreatedAudioFileResponse;
import com.example.text.to.speech.service.dto.SpeechTextRequest;
import com.example.text.to.speech.service.dto.VoiceDto;
import com.example.text.to.speech.service.enums.Voice;
import com.example.text.to.speech.service.service.TextToSpeechService;
import ie.corballis.sox.WrongParametersException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("voice/list")
    public List<VoiceDto> getVoices() {
        return Arrays.stream(Voice.values())
                .map(voice -> new VoiceDto(voice.name(), voice.getLanguageCode()))
                .collect(Collectors.toList());
    }
}
