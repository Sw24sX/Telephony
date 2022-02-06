package com.example.telephony.tts.service;

import com.example.telephony.tts.common.SpeechFileHelper;
import com.example.telephony.tts.common.TTSPropertiesHelper;
import com.example.telephony.tts.exception.TextToSpeechException;
import com.example.telephony.tts.persistance.enums.EngineName;
import com.example.telephony.tts.persistance.enums.TTSExceptionMessage;
import com.example.telephony.tts.persistance.enums.TTSProperty;
import com.example.telephony.tts.service.engine.google.GoogleTextToSpeechEngine;
import com.example.telephony.tts.service.engine.microsoft.desktop.MicrosoftTextToSpeechEngine;
import org.springframework.core.env.Environment;

import java.io.File;
import java.util.function.Function;

public class TTSEngineManager {
    private final Function<String, File> ttsFunction;
    private final Environment environment;

    public TTSEngineManager(Environment environment) {
        this.ttsFunction = findTtsFunction(environment);
        this.environment = environment;
    }

    private static Function<String, File> findTtsFunction(Environment environment) {
        String engineName = TTSPropertiesHelper.getProperty(TTSProperty.TTS_ENGINE, environment);
        EngineName name = EngineName.getByName(engineName);
        switch (name) {
            case GOOGLE:
                return new GoogleTextToSpeechEngine(environment).getTextToSpeechFunction(engineName);
            case MICROSOFT:
                return new MicrosoftTextToSpeechEngine(environment).getTextToSpeechFunction(engineName);
            default:
                throw new TextToSpeechException(TTSExceptionMessage.NOT_CORRECT_NAME_ENGINE);
        }
    }

    public File textToSpeech(String text) {
        File tempFile = ttsFunction.apply(text);
        SoxReformat soxReformat = new SoxReformat(environment);
        File result = soxReformat.reformatFile(tempFile);
        SpeechFileHelper.deleteTempFiles(TTSPropertiesHelper.getProperty(TTSProperty.TTS_TEMP_FILE, environment));
        return result;
    }
}
