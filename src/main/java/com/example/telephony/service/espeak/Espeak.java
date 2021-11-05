package com.example.telephony.service.espeak;

import com.example.telephony.common.Properties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class Espeak {
    private final Path path;
    private static final String COMMAND_ESPEAK = "espeak";
    private static final String RU = "ru-RU";
    private static final String EN = "en-US";

    public Espeak(Environment environment) {
        this.path = Paths.get(Properties.getProperty(environment, "file.generated.path"));
    }

    public String textToFile(String text) {
        Voice voice = new Voice();
        voice.setName("ru");
        voice.setSpeed(100);
        voice.setAmplitude(100);
        voice.setPitch(30);

        String uniqueFileName = getUniqueFileName();
        Path pathToNewFile = path.resolve(uniqueFileName).toAbsolutePath();
        execute(COMMAND_ESPEAK,
                "-v", buildVariant(voice),
                "-w", pathToNewFile.toString(),
                "-p", Integer.toString(voice.getPitch()),
                "-a", Integer.toString(voice.getAmplitude()),
                "-s", Integer.toString(voice.getSpeed()),
                text);
        return uniqueFileName;
    }

    private String getUniqueFileName() {
        return String.format("%s.wav", UUID.randomUUID());
    }

    private String buildVariant(Voice voice) {
        StringBuilder builder = new StringBuilder();
        if (voice.getName() != null && !voice.getName().isEmpty()) {
            builder.append(voice.getName());
        }

        if (voice.getVariant() != null && !voice.getVariant().isEmpty()) {
            builder.append("+");
            builder.append(voice.getVariant());
        }

        return builder.toString();
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
