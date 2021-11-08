package com.example.telephony.service;

import com.example.telephony.common.Properties;
import com.example.telephony.domain.GeneratedSound;
import com.example.telephony.enums.ExceptionMessage;
import com.example.telephony.enums.SpeechVoice;
import com.example.telephony.exception.EntityNotFoundException;
import com.example.telephony.repository.GeneratedSoundRepository;
import com.example.telephony.service.tts.MicrosoftTextToSpeech;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class TTSService {
    private final MicrosoftTextToSpeech microsoftTextToSpeech;
    private final Environment environment;
    private final GeneratedSoundRepository generatedSoundRepository;

    public TTSService(MicrosoftTextToSpeech microsoftTextToSpeech, Environment environment, GeneratedSoundRepository generatedSoundRepository) {
        this.microsoftTextToSpeech = microsoftTextToSpeech;
        this.environment = environment;
        this.generatedSoundRepository = generatedSoundRepository;
    }

    public GeneratedSound textToFile(String text, SpeechVoice voice) {
        String fileName = microsoftTextToSpeech.textToFile(text, voice);
        GeneratedSound generatedSound = new GeneratedSound();
        generatedSound.setPath(getFilePath(fileName).toString());
        generatedSound.setUri(buildFileUri(fileName));
        return generatedSoundRepository.save(generatedSound);
    }

    private String buildFileUri(String fileName) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(Properties.getProperty(environment, "server.address"))
                .port(Properties.getProperty(environment, "server.port"))
                .path(Properties.getProperty(environment, "file.generated.url") + fileName).build();
        return uriComponents.toString();
    }

    private Path getFilePath(String fileName) {
        return Paths.get(Properties.getProperty(environment, "file.generated.path")).resolve(fileName);
    }

    public List<GeneratedSound> getAll() {
        return generatedSoundRepository.findAll();
    }

    public GeneratedSound getById(Long id) {
        return generatedSoundRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format(ExceptionMessage.GENERATED_FILE_NOT_FOUND.getMessage(), id)));
    }

    public void delete(Long id) {
        GeneratedSound generatedSound = getById(id);
        generatedSoundRepository.delete(generatedSound);
    }
}
