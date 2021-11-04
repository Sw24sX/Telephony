package com.example.telephony.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class TTSService {
    private static final String RU = "ru-RU";
    private static final String EN = "en-US";
    private static final String COMMAND_ESPEAK = "espeak";

    public void tts(String text) {
//        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()){
//
//        } catch (Exception e) {
//            throw new TelephonyException(e.getMessage());
//        }

//        freeTTS(text);
        espeak(text);
    }

    private void espeak(String text) {
//        Espeak espeak = new Espeak();
//        espeak.speak(text);
        Path path = Paths.get("file\\generate");
        execute(COMMAND_ESPEAK, "-w", path.toAbsolutePath().resolve("text.wav").toString(), text);
    }

    private static void execute(final String ... command) {
        String threadName = "espeak";

        new Thread(new Runnable() {
            public void run() {
                ProcessBuilder b = new ProcessBuilder(command);
                b.redirectErrorStream(true);
                try {
                    Process process = b.start();

                    readErrors(process);
                    process.waitFor();
                    process.destroy();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
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
