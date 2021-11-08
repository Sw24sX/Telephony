package com.example.telephony.service.tts;

import com.example.telephony.enums.SpeechVoice;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpeechCommandLineScriptBuilder {
    private final List<String> commands;
    private String voice;
    private String path;
    private String text;

    public SpeechCommandLineScriptBuilder() {
        commands = new ArrayList<>(Arrays.asList(
                "powershell.exe",
                "Add-Type -AssemblyName System.speech;",
                "$speech = New-Object System.Speech.Synthesis.SpeechSynthesizer;"
        ));

        this.voice = "$speech.SelectVoice('%s');";
        this.path = "$speech.SetOutputToWaveFile('%s');";
        this.text = "$speech.speak('%s')";
    }

    public SpeechCommandLineScriptBuilder setPath(Path pathToFile) {
        this.path = String.format(this.path, pathToFile.toAbsolutePath());
        return this;
    }

    public SpeechCommandLineScriptBuilder setVoice(SpeechVoice speechVoice) {
        this.voice = String.format(this.voice, speechVoice.getValue());
        return this;
    }

    public SpeechCommandLineScriptBuilder setText(String textToSpeech) {
        this.text = String.format(this.text, textToSpeech);
        return this;
    }

    public List<String> build() {
        commands.add(voice);
        commands.add(path);
        commands.add(text);
        return commands;
    }
}
