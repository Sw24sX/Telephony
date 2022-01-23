package com.example.text.to.speech.service.service;

import com.example.text.to.speech.service.dto.CreatedAudioFileResponse;
import com.example.text.to.speech.service.dto.SpeechTextRequest;
import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@Service
public class TextToSpeechService {
    public CreatedAudioFileResponse textToSpeech(SpeechTextRequest request) throws IOException {
        CreatedAudioFileResponse result = new CreatedAudioFileResponse(request);

        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
            SynthesisInput input = createInput(request);
            VoiceSelectionParams voice = createVoice(request);
            AudioConfig audioConfig = createAudioConfig();

            SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);
            String fileName = writeOutputFile(response.getAudioContent());
            result.setName(fileName);
        }

        return result;
    }

    private SynthesisInput createInput(SpeechTextRequest request) {
        return SynthesisInput.newBuilder()
                .setText(request.getText())
                .build();
    }

    private VoiceSelectionParams createVoice(SpeechTextRequest request) {
        return VoiceSelectionParams.newBuilder()
                .setLanguageCode(request.getVoice().getLanguageCode())
                .setSsmlGender(request.getVoice().getVoiceGender())
                .build();
    }

    private AudioConfig createAudioConfig() {
        // TODO: 23.01.2022 try set correct parameters
        return AudioConfig.newBuilder()
                .setAudioEncoding(AudioEncoding.MP3)
                .build();
    }

    private String writeOutputFile(ByteString audioContents) throws IOException {
        String fileName = String.format("%s.wav", UUID.randomUUID());
        try (OutputStream out = new FileOutputStream(fileName)) {
            out.write(audioContents.toByteArray());
            // TODO: 23.01.2022 log this
            System.out.printf("Audio content written to file \"%s\"", fileName);
        }

        return fileName;
    }
}
