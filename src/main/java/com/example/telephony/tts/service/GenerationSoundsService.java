package com.example.telephony.tts.service;

import com.example.telephony.common.PropertiesHelper;
import com.example.telephony.tts.persistance.domain.GeneratedSound;
import com.example.telephony.enums.exception.messages.ExceptionMessage;
import com.example.telephony.exception.EntityNotFoundException;
import com.example.telephony.tts.persistance.repository.GeneratedSoundRepository;
import com.example.telephony.tts.service.engine.microsoft.desktop.MicrosoftTextToSpeechEngine;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

/**
 *
 */
@Service
public class GenerationSoundsService {
    private final MicrosoftTextToSpeechEngine microsoftTextToSpeech;
    private final Environment environment;
    private final GeneratedSoundRepository generatedSoundRepository;
    private final Path generatedFilePath;
    private final TTSEngineManager ttsEngineManager;

    public GenerationSoundsService(MicrosoftTextToSpeechEngine microsoftTextToSpeech, Environment environment,
                                   GeneratedSoundRepository generatedSoundRepository, TTSEngineManager ttsEngineManager) {
        this.microsoftTextToSpeech = microsoftTextToSpeech;
        this.environment = environment;
        this.generatedSoundRepository = generatedSoundRepository;
        generatedFilePath = Paths.get(PropertiesHelper.getProperty(environment, "file.generated.path"));
        this.ttsEngineManager = ttsEngineManager;
    }

    /**
     *
     * @param text
     * @return
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
     *
     * @return
     */
    public List<GeneratedSound> getAll() {
        return generatedSoundRepository.findAll();
    }

    /**
     *
     * @param id
     * @return
     */
    public GeneratedSound getById(Long id) {
        return generatedSoundRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format(ExceptionMessage.GENERATED_FILE_NOT_FOUND.getMessage(), id)));
    }

    /**
     *
     * @param id
     */
    public void delete(Long id) {
        GeneratedSound generatedSound = getById(id);
        if (!new File(generatedSound.getPath()).delete()) {
            System.out.println(String.format(ExceptionMessage.FILE_NOT_FOUND.getMessage(), generatedSound.getPath()));
        }
        generatedSoundRepository.delete(generatedSound);
    }

    /**
     *
     * @param sounds
     */
    public void deleteAll(Collection<GeneratedSound> sounds) {
        generatedSoundRepository.deleteAll(sounds);
    }
}
