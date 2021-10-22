package com.example.telephony.controller;

import com.example.telephony.domain.Sound;
import com.example.telephony.dto.SoundDto;
import com.example.telephony.mapper.SoundMapper;
import com.example.telephony.service.SoundService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("sound")
public class SoundController {
    private final SoundService soundService;
    private final SoundMapper soundMapper;

    public SoundController(SoundService soundService, SoundMapper soundMapper) {
        this.soundService = soundService;
        this.soundMapper = soundMapper;
    }

    @GetMapping()
    public List<SoundDto> getAll() {
        return soundMapper.listSoundToListSoundDto(soundService.getAll());
    }

    @GetMapping("{id}")
    public SoundDto getById(@PathVariable("id") Long id) {
        Sound sound = soundService.getById(id);
        return soundMapper.soundToSoundDto(sound);
    }

    @PostMapping()
    public SoundDto create(@RequestParam("file") MultipartFile multipartFile) {
        Sound sound = soundService.create(multipartFile);
        return soundMapper.soundToSoundDto(sound);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        soundService.delete(id);
    }
}
