package com.example.telephony.tts.service;

import com.example.telephony.common.PropertiesHelper;
import com.example.telephony.tts.persistance.domain.GeneratedSound;
import com.example.telephony.enums.exception.messages.ExceptionMessage;
import com.example.telephony.exception.EntityNotFoundException;
import com.example.telephony.tts.persistance.repository.GeneratedSoundRepository;
import com.example.telephony.tts.service.engine.TTSEngineManager;
import com.example.telephony.tts.service.engine.microsoft.desktop.MicrosoftTextToSpeechEngine;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Service for work with GeneratedSound entity
 */
@Service
public class GenerationSoundsService {
    private final Environment environment;
    private final GeneratedSoundRepository generatedSoundRepository;
    private final TTSEngineManager ttsEngineManager;

    public GenerationSoundsService(Environment environment, GeneratedSoundRepository generatedSoundRepository,
                                   TTSEngineManager ttsEngineManager) {
        this.environment = environment;
        this.generatedSoundRepository = generatedSoundRepository;
        this.ttsEngineManager = ttsEngineManager;
    }

    /**
     * Get audio from text
     * @param text text for synthesize
     * @return generated sound file
     */
    public GeneratedSound textToGeneratedFile(String text) {
        String preparedText = StringUtils.trim(text);
        GeneratedSound generatedSoundDb = generatedSoundRepository.findGeneratedSoundByText(preparedText);
        return generatedSoundDb == null ? generateNewFile(preparedText) : generatedSoundDb;
    }

    private GeneratedSound generateNewFile(String text) {
        File synthesizeFile = ttsEngineManager.textToSpeech(text);
        GeneratedSound generatedSound = new GeneratedSound();
        generatedSound.setPath(synthesizeFile.getPath());
        generatedSound.setUri(buildFileUri(synthesizeFile.getName()));
        generatedSound.setText(text);
        return generatedSoundRepository.save(generatedSound);
    }

    private String buildFileUri(String fileName) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(PropertiesHelper.getProperty(environment, "server.address"))
                .port(PropertiesHelper.getProperty(environment, "server.port"))
                .path(PropertiesHelper.getProperty(environment, "file.generated.url") + fileName).build();
        return uriComponents.toString();
    }

    /**
     * Get all generated files
     * @return List all generated file
     */
    public List<GeneratedSound> getAll() {
        return generatedSoundRepository.findAll();
    }

    /**
     * Get generated file by id
     * @param id Generated file id
     * @return generated file or EntityNotFoundException
     */
    public GeneratedSound getById(Long id) {
        return generatedSoundRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format(ExceptionMessage.GENERATED_FILE_NOT_FOUND.getMessage(), id)));
    }

    /**
     * Delete generated file from file system and database
     * @param id Generated file id
     */
    public void delete(Long id) {
        GeneratedSound generatedSound = getById(id);
        if (!new File(generatedSound.getPath()).delete()) {
            String message = String.format(ExceptionMessage.FILE_NOT_FOUND.getMessage(), generatedSound.getPath());
            System.out.println(message);
        }
        generatedSoundRepository.delete(generatedSound);
    }
}
