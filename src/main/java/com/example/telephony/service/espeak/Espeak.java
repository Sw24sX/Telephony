package com.example.telephony.service.espeak;

import com.example.telephony.common.Properties;
import com.example.telephony.enums.SpeechVoice;
import com.profesorfalken.jpowershell.PowerShell;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class Espeak {
    private final Path path;

    public Espeak(Environment environment) {
        this.path = Paths.get(Properties.getProperty(environment, "file.generated.path"));
    }

    public String textToFile(String text, SpeechVoice speechVoice) {
        String uniqueFileName = getUniqueFileName();
        Path pathToNewFile = path.resolve(uniqueFileName);
        List<String> commandLineScript = new SpeechCommandLineScriptBuilder()
                .setText(text)
                .setPath(pathToNewFile)
                .setVoice(speechVoice)
                .build();
//        PowerShell powerShell = PowerShell.openSession();
//        powerShell.executeCommand(commandLineScript.get(0));
//        powerShell.executeCommand(commandLineScript.get(1));
//        powerShell.executeCommand(commandLineScript.get(2));
//        powerShell.executeCommand(commandLineScript.get(3));
//        powerShell.executeCommand(commandLineScript.get(4));
//        powerShell.close();

        String[] list = commandLineScript.toArray(new String[0]);
        execute(list);
        return uniqueFileName;
    }

    private String getUniqueFileName() {
        return String.format("%s.wav", UUID.randomUUID());
    }

    private static void execute(final String ... command) {
        String threadName = "powershell.exe";

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
