package com.example.telephony.service;

import com.example.telephony.domain.Sound;
import com.example.telephony.enums.ExceptionMessage;
import com.example.telephony.exception.EntityNotFoundException;
import com.example.telephony.exception.TelephonyException;
import com.example.telephony.repository.SoundRepository;
import jdk.jshell.spi.ExecutionControl;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class SoundService {
    private final SoundRepository soundRepository;

    public SoundService(SoundRepository soundRepository) {
        this.soundRepository = soundRepository;
    }

    public Sound getById(Long id) {
        return soundRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException(String.format(ExceptionMessage.SOUND_NOT_FOUND.getMessage(), id)));
    }

    public List<Sound> getAll() {
        return soundRepository.findAll();
    }

    public Sound create(MultipartFile multipartFile) {
        try {
            return createFile(multipartFile);
        } catch (IOException e) {
            throw new TelephonyException(e.getMessage());
        }
    }

    private Sound createFile(MultipartFile multipartFile) throws IOException {
        CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) multipartFile;
        throw new NotImplementedException();
    }

    public Sound update(Long id, Sound sound) {
        sound.setId(id);
        return soundRepository.save(sound);
    }

    public void delete(Long id) {
        Sound sound = getById(id);
        soundRepository.delete(sound);
    }
}