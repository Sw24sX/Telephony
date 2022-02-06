package com.example.telephony.controller;

import com.example.telephony.common.GlobalMapping;
import com.example.telephony.domain.GeneratedSound;
import com.example.telephony.dto.GeneratedSoundDto;
import com.example.telephony.enums.SpeechVoice;
import com.example.telephony.mapper.GeneratedSoundMapper;
import com.example.telephony.service.GenerationSoundsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(GlobalMapping.API + "text-to-file")
public class TTSController {
    private final GenerationSoundsService generationSoundsService;
    private final GeneratedSoundMapper generatedSoundMapper;

    public TTSController(GenerationSoundsService generationSoundsService, GeneratedSoundMapper generatedSoundMapper) {
        this.generationSoundsService = generationSoundsService;
        this.generatedSoundMapper = generatedSoundMapper;
    }

    @PostMapping
    public GeneratedSoundDto textToFile(@RequestParam("text") String text,
                                        @RequestParam("voice") SpeechVoice voice) {
        GeneratedSound generatedSound = generationSoundsService.textToFile(text, voice);
        return generatedSoundMapper.generatedSoundDtoToGeneratedSound(generatedSound);
    }

    @GetMapping
    public List<GeneratedSoundDto> getAllGeneratedFiles() {
        return generatedSoundMapper.listGeneratedSoundToListGeneratedSoundDto(generationSoundsService.getAll());
    }

    @GetMapping("{id}")
    public GeneratedSoundDto getById(@PathVariable("id") Long id) {
        return generatedSoundMapper.generatedSoundDtoToGeneratedSound(generationSoundsService.getById(id));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        generationSoundsService.delete(id);
    }
}
