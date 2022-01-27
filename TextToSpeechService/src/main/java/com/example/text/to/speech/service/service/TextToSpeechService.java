package com.example.text.to.speech.service.service;

import com.example.text.to.speech.service.common.SpeechFileHelper;
import com.example.text.to.speech.service.dto.CreatedAudioFileResponse;
import com.example.text.to.speech.service.dto.SpeechTextRequest;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TextToSpeechService {
    public final SoxReformat soxReformat;

    public TextToSpeechService(SoxReformat soxReformat) {
        this.soxReformat = soxReformat;
    }

    public List<CreatedAudioFileResponse> textToSpeechList(List<SpeechTextRequest> requests) {
        return requests.stream().map(this::textToSpeech).collect(Collectors.toList());
    }

    public CreatedAudioFileResponse textToSpeech(SpeechTextRequest request) {
        CreatedAudioFileResponse result = new CreatedAudioFileResponse(request);

        GoogleCloudTTS tts = new GoogleCloudTTS();
        ByteString audioContents = tts.textToSpeech(request);
        File tempFile = SpeechFileHelper.writeOutputFile(audioContents);

        File resultFile = soxReformat.reformatFile(tempFile);
        result.setName(resultFile.getName());
        result.setUri(resultFile.getAbsolutePath());

        return result;
    }
}
