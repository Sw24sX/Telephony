package com.example.text.to.speech.service.service;

import com.example.text.to.speech.service.common.PropertiesHelper;
import com.example.text.to.speech.service.common.SpeechFileHelper;
import com.example.text.to.speech.service.dto.CreatedAudioFileResponse;
import com.example.text.to.speech.service.dto.SpeechTextRequest;
import com.example.text.to.speech.service.enums.CustomApplicationProperty;
import com.google.protobuf.ByteString;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main service for synthesis file from text
 */
@Service
public class TextToSpeechService {
    private final Environment environment;

    public TextToSpeechService(Environment environment) {
        this.environment = environment;
    }

    /**
     * Create list file from list texts
     * @param requests List requests for create files
     * @return List synthesis files
     */
    public List<CreatedAudioFileResponse> textToSpeechList(List<SpeechTextRequest> requests) {
        // TODO: 27.01.2022 multithreading
        List<CreatedAudioFileResponse> result = requests.stream().map(this::audioFileSynthesis).collect(Collectors.toList());
        clearTempDirectory();
        return result;
    }

    /**
     * Create file from text
     * @param request Request for create file
     * @return Synthesis file
     */
    public CreatedAudioFileResponse textToSpeech(SpeechTextRequest request) {
        CreatedAudioFileResponse result = audioFileSynthesis(request);
        clearTempDirectory();
        return result;
    }

    private CreatedAudioFileResponse audioFileSynthesis(SpeechTextRequest request) {
        CreatedAudioFileResponse result = new CreatedAudioFileResponse(request);

        GoogleCloudTTS tts = new GoogleCloudTTS();
        ByteString audioContents = tts.textToSpeech(request);
        String tempFilePath = PropertiesHelper.getApplicationProperty(CustomApplicationProperty.TTS_TEMP_FILE, environment);
        File tempFile = SpeechFileHelper.writeTempFile(audioContents, tempFilePath);

        File resultFile = new SoxReformat(environment).reformatFile(tempFile);
        result.setName(resultFile.getName());
        result.setUri(resultFile.toURI().toString());
        result.setAbsolutePath(resultFile.getAbsolutePath());

        return result;
    }

    private void clearTempDirectory() {
        String path = PropertiesHelper.getApplicationProperty(CustomApplicationProperty.TTS_TEMP_FILE, environment);
        SpeechFileHelper.deleteTempFiles(path);
    }
}
