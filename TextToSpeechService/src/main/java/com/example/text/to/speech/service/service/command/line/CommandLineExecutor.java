package com.example.text.to.speech.service.service.command.line;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CommandLineExecutor {
    private final List<String> commands;

    public CommandLineExecutor() {
        commands = new ArrayList<>();
    }

    public void addCommands(CommandLineCommands commandLineCommands) {
        commands.addAll(commandLineCommands.getCommands());
    }

    public void execute() {
        CommandLineExecutor.execute(commands.toArray(new String[0]));
        commands.clear();
    }

    private static void execute(final String... command) {
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
                    // TODO: 24.01.2022 log this
                    e.printStackTrace();
                }
            }

            private void readErrors(Process process) throws IOException {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = in.readLine()) != null) {
                        // TODO: 24.01.2022 log this
                        System.err.println(line);
                    }
                }
            }
        }, threadName).start();
    }
}
