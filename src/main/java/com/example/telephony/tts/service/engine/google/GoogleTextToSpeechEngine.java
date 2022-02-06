package com.example.telephony.tts.service.engine.google;

import com.example.telephony.tts.common.TTSPropertiesHelper;
import com.example.telephony.tts.common.SpeechFileHelper;
import com.example.telephony.tts.persistance.enums.GoogleSpeechVoice;
import com.example.telephony.tts.exception.TextToSpeechException;
import com.example.telephony.tts.persistance.enums.TTSExceptionMessage;
import com.example.telephony.tts.persistance.enums.TTSProperty;
import com.example.telephony.tts.service.engine.TextToSpeechEngine;
import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.function.Function;

public class GoogleTextToSpeechEngine implements TextToSpeechEngine {
    private static final GoogleSpeechVoice DEFAULT_VOICE = GoogleSpeechVoice.RUSSIAN;
    private final Environment environment;

    public GoogleTextToSpeechEngine(Environment environment) {
        this.environment = environment;
    }

    @Override
    public File textToSpeech(String text) {
        ByteString bytesFile = textToSpeech(text, DEFAULT_VOICE);
        return SpeechFileHelper.writeTempFile(bytesFile, TTSPropertiesHelper.getProperty(TTSProperty.TTS_TEMP_FILE, environment));
    }

    @Override
    public Function<String, File> getTextToSpeechFunction(String name) {
        if (name.toLowerCase(Locale.ROOT).equals("google")) {
            return this::textToSpeech;
        }

        throw new TextToSpeechException(TTSExceptionMessage.NOT_CORRECT_NAME_ENGINE);
    }

    /**
     * Get bytes for audio from text
     * @param text Text for synthesize audio
     * @param speechVoice Voice synthesize audio
     * @return Bytes for audio file
     */
    public ByteString textToSpeech(String text, GoogleSpeechVoice speechVoice) {
        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
            SynthesisInput input = createInput(text);
            VoiceSelectionParams voice = createVoice(speechVoice);
            AudioConfig audioConfig = createAudioConfig();

            SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);
            return response.getAudioContent();
        } catch (IOException e) {
            throw new TextToSpeechException(e.getMessage(), e.getCause());
        }
    }

    private SynthesisInput createInput(String text) {
        return SynthesisInput.newBuilder()
                .setText(text)
                .build();
    }

    private VoiceSelectionParams createVoice(GoogleSpeechVoice voice) {
        return VoiceSelectionParams.newBuilder()
                .setLanguageCode(voice.getLanguageCode())
                .setSsmlGender(voice.getVoiceGender())
                .build();
    }

    private AudioConfig createAudioConfig() {
        return AudioConfig.newBuilder()
                .setAudioEncoding(AudioEncoding.LINEAR16)
                .setSampleRateHertz(8000)
                .build();
    }
}
