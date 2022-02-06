package com.example.telephony.tts.service.engine.microsoft.desktop;

import com.example.telephony.common.PropertiesHelper;
import com.example.telephony.tts.exception.TextToSpeechException;
import com.example.telephony.tts.persistance.enums.EngineName;
import com.example.telephony.tts.persistance.enums.MicrosoftSpeechVoice;
import com.example.telephony.tts.persistance.enums.TTSExceptionMessage;
import com.example.telephony.tts.service.engine.TextToSpeechEngine;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.function.Function;

@Component
public class MicrosoftTextToSpeechEngine implements TextToSpeechEngine {
    private final Path path;
    private static final MicrosoftSpeechVoice DEFAULT_VOICE = MicrosoftSpeechVoice.IRINA;

    public MicrosoftTextToSpeechEngine(Environment environment) {
        this.path = Paths.get(PropertiesHelper.getProperty(environment, "file.generated.path"));
    }

    @Override
    public File textToSpeech(String text) {
        return new File(textToFile(text, DEFAULT_VOICE));
    }

    @Override
    public Function<String, File> getTextToSpeechFunction(String name) {
        if (name.toLowerCase(Locale.ROOT).equals(EngineName.MICROSOFT.getName())) {
            return this::textToSpeech;
        }

        throw new TextToSpeechException(TTSExceptionMessage.NOT_CORRECT_NAME_ENGINE);
    }

    public String textToFile(String text, MicrosoftSpeechVoice speechVoice) {
        Path pathToFirstFile = path.resolve(getUniqueFileName());
        String endFileName = getUniqueFileName();
        Path pathToEndFile = path.resolve(endFileName);

        List<String> commandLineScript = new SpeechCommandLineScriptBuilder()
                .setText(text)
                .setPath(pathToFirstFile)
                .setVoice(speechVoice)
                .setSox(pathToFirstFile.toString(), pathToEndFile.toString())
                .build();

        String[] list = commandLineScript.toArray(new String[0]);
        execute(list);
        return endFileName;
    }

    private String getUniqueFileName() {
        return String.format("%s.wav", UUID.randomUUID());
    }

    private static void execute(final String ... command) {
        String threadName = "powershell.exe";

        new Thread(new Runnable() {

            @Override
            public void run() {
                ProcessBuilder processBuilder = new ProcessBuilder(command);
                processBuilder.redirectErrorStream(true);
                try {
                    Process process = processBuilder.start();
                    readErrors(process);
                    process.waitFor();
                    process.destroy();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }

            private void readErrors(Process process) throws IOException {
                BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    System.err.println(line);
                }
            }
        }, threadName).start();
    }

}
