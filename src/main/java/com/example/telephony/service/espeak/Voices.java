package com.example.telephony.service.espeak;

import com.example.telephony.enums.SpeechVoice;

import java.nio.file.Path;
import java.util.List;

public class Voices {
    public static List<String> getIrinaScript(String text, Path path) {
        return new SpeechCommandLineScriptBuilder()
                .setPath(path)
                .setVoice(SpeechVoice.IRINA)
                .setText(text)
                .build();
    }

    public static List<String> getZiraScript(String text, Path path) {
        return new SpeechCommandLineScriptBuilder()
                .setPath(path)
                .setVoice(SpeechVoice.ZIRA)
                .setText(text)
                .build();
    }
}
