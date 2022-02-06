package com.example.telephony.tts.service.engine;

import java.io.File;
import java.util.function.Function;

public interface TextToSpeechEngine {
    /**
     * Synthesize audio from text
     * @param text Text for synthesizing
     * @return Synthesized file
     */
    File textToSpeech(String text);

    /**
     * Get function textToSpeech
     * @param name Engine name
     * @return function textToSpeech
     */
    Function<String, File> getTextToSpeechFunction(String name);
}
