package com.example.telephony.service.espeak;

import com.example.telephony.enums.SpeechVoice;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpeechCommandLineScriptBuilder {
    private List<String> commands;
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
        String fullVoiceCommand = String.format(this.voice, speechVoice.getValue());

        this.voice = fullVoiceCommand;
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
