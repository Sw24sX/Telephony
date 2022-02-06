package com.example.telephony.tts.rest;

import com.example.telephony.common.GlobalMappingUtils;
import com.example.telephony.tts.persistance.domain.GeneratedSound;
import com.example.telephony.tts.dto.GeneratedSoundDto;
import com.example.telephony.tts.mapper.GeneratedSoundMapper;
import com.example.telephony.tts.service.GenerationSoundsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(GlobalMappingUtils.API + "text-to-file")
public class TTSController {
    private final GenerationSoundsService generationSoundsService;
    private final GeneratedSoundMapper generatedSoundMapper;

    public TTSController(GenerationSoundsService generationSoundsService, GeneratedSoundMapper generatedSoundMapper) {
        this.generationSoundsService = generationSoundsService;
        this.generatedSoundMapper = generatedSoundMapper;
    }

    @PostMapping
    public GeneratedSoundDto textToFile(@RequestParam("text") String text) {
        GeneratedSound generatedSound = generationSoundsService.textToGeneratedFile(text);
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
