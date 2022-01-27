package com.example.text.to.speech.service.service;

import com.example.text.to.speech.service.common.PropertiesHelper;
import com.example.text.to.speech.service.common.SpeechFileHelper;
import com.example.text.to.speech.service.domain.GeneratedSound;
import com.example.text.to.speech.service.dto.CreatedAudioFileResponse;
import com.example.text.to.speech.service.dto.SpeechTextRequest;
import com.example.text.to.speech.service.enums.CustomApplicationProperty;
import com.example.text.to.speech.service.mapper.GeneratedSoundMapper;
import com.example.text.to.speech.service.repository.GeneratedSoundRepository;
import com.google.protobuf.ByteString;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main service for synthesis file from text
 */
@Service
public class TextToSpeechService {
    private final Environment environment;
    private final GeneratedSoundRepository generatedSoundRepository;
    private final GeneratedSoundMapper soundMapper;

    public TextToSpeechService(Environment environment, GeneratedSoundRepository generatedSoundRepository,
                               GeneratedSoundMapper soundMapper) {
        this.environment = environment;
        this.generatedSoundRepository = generatedSoundRepository;
        this.soundMapper = soundMapper;
    }

    /**
     * Create list file from list texts
     * @param requests List requests for create files
     * @return List synthesis files
     */
    public List<CreatedAudioFileResponse> textToSpeechList(List<SpeechTextRequest> requests) {
        // TODO: 27.01.2022 multithreading
        List<GeneratedSound> result = requests.stream()
                .map(this::audioFileSynthesis)
                .collect(Collectors.toList());
        clearTempDirectory();
        return soundMapper.fromGeneratedSound(result);
    }

    /**
     * Create file from text
     * @param request Request for create file
     * @return Synthesis file
     */
    public CreatedAudioFileResponse textToSpeech(SpeechTextRequest request) {
        GeneratedSound result = audioFileSynthesis(request);
        clearTempDirectory();
        return soundMapper.fromGeneratedSound(result);
    }

    private GeneratedSound audioFileSynthesis(SpeechTextRequest request) {
        GeneratedSound sound = tryGetExistedFile(request.getText());
        if (sound != null) {
            return sound;
        } else {
            sound = new GeneratedSound();
        }

        GoogleCloudTTS tts = new GoogleCloudTTS();
        ByteString audioContents = tts.textToSpeech(request);
        String tempFilePath = PropertiesHelper.getApplicationProperty(CustomApplicationProperty.TTS_TEMP_FILE, environment);
        File tempFile = SpeechFileHelper.writeTempFile(audioContents, tempFilePath);

        File resultFile = new SoxReformat(environment).reformatFile(tempFile);

        sound.setText(request.getText());
        sound.setPath(resultFile.getAbsolutePath());
        sound.setUri(buildUri(resultFile));

        return generatedSoundRepository.save(sound);
    }

    private GeneratedSound tryGetExistedFile(String text) {
        return generatedSoundRepository.findGeneratedSoundByText(text);
    }

    private String buildUri(File file) {
        String relativePath = PropertiesHelper.getApplicationProperty(CustomApplicationProperty.TTS_RESULT_URL, environment) +
                file.getName();

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(PropertiesHelper.getApplicationProperty(CustomApplicationProperty.SERVER_ADDRESS, environment))
                .port(PropertiesHelper.getApplicationProperty(CustomApplicationProperty.SERVER_PORT, environment))
                .path(relativePath).build();
        return uriComponents.toString();
    }

    private void clearTempDirectory() {
        String path = PropertiesHelper.getApplicationProperty(CustomApplicationProperty.TTS_TEMP_FILE, environment);
        SpeechFileHelper.deleteTempFiles(path);
    }
}
