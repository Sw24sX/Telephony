package com.example.text.to.speech.service.service;

import com.example.text.to.speech.service.dto.SpeechTextRequest;
import com.example.text.to.speech.service.exception.TextToSpeechException;
import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;

import java.io.IOException;

/**
 * Use Google Clout tts
 */
public class GoogleCloudTTS {

    /**
     * Get bytes for audio from text
     * @param request Request for create bytes for audio
     * @return Bytes for audio file
     */
    public ByteString textToSpeech(SpeechTextRequest request) {
        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
            SynthesisInput input = createInput(request);
            VoiceSelectionParams voice = createVoice(request);
            AudioConfig audioConfig = createAudioConfig();

            SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);
            return response.getAudioContent();
        } catch (IOException e) {
            throw new TextToSpeechException(e.getMessage(), e.getCause());
        }
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
        return AudioConfig.newBuilder()
                .setAudioEncoding(AudioEncoding.LINEAR16)
                .setSampleRateHertz(8000)
                .build();
    }
}
