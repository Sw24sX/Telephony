package com.example.telephony.controller;

import com.example.telephony.domain.GeneratedSound;
import com.example.telephony.dto.GeneratedSoundDto;
import com.example.telephony.mapper.GeneratedSoundMapper;
import com.example.telephony.service.TTSService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("text-to-file")
public class TTSController {
    private final TTSService ttsService;
    private final GeneratedSoundMapper generatedSoundMapper;

    public TTSController(TTSService ttsService, GeneratedSoundMapper generatedSoundMapper) {
        this.ttsService = ttsService;
        this.generatedSoundMapper = generatedSoundMapper;
    }

    @PostMapping
    public GeneratedSoundDto textToFile(@RequestParam("text") String text) {
        GeneratedSound generatedSound = ttsService.textToFile(text);
        return generatedSoundMapper.generatedSoundDtoToGeneratedSound(generatedSound);
    }

    @GetMapping
    public List<GeneratedSoundDto> getAllGeneratedFiles() {
        return generatedSoundMapper.listGeneratedSoundToListGeneratedSoundDto(ttsService.getAll());
    }

    @GetMapping("{id}")
    public GeneratedSoundDto getById(@PathVariable("id") Long id) {
        return generatedSoundMapper.generatedSoundDtoToGeneratedSound(ttsService.getById(id));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        ttsService.delete(id);
    }
}
