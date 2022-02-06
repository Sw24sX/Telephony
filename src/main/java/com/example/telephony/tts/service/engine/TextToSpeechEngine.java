package com.example.telephony.tts.service.engine;

import java.io.File;
import java.util.function.Function;

public interface TextToSpeechEngine {
    File textToSpeech(String text);
    Function<String, File> getTextToSpeechFunction(String name);
}
