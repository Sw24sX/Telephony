package com.example.telephony.controller;

import com.example.telephony.service.TTSService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tts")
public class TTSController {
    private final TTSService ttsService;

    public TTSController(TTSService ttsService) {
        this.ttsService = ttsService;
    }

    @PostMapping
    public void tts(@RequestParam("text") String text) {
        ttsService.tts(text);
    }
}
