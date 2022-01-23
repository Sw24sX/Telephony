package com.example.telephony.service.tts;

import com.example.telephony.common.PropertiesHelper;
import com.example.telephony.enums.SpeechVoice;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Component
public class MicrosoftTextToSpeech {
    private final Path path;

    public MicrosoftTextToSpeech(Environment environment) {
        this.path = Paths.get(PropertiesHelper.getProperty(environment, "file.generated.path"));
    }

    public String textToFile(String text, SpeechVoice speechVoice) {
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
