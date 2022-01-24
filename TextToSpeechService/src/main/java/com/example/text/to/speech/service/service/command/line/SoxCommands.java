package com.example.text.to.speech.service.service.command.line;

public class SoxCommands extends CommandLineCommands {
    private final String patternCommand;

    public SoxCommands() {
        patternCommand = "sox -r 8000 -b 16 -e signed-integer -c 1 %s %s speed 3";
    }

    public void addCommand(String inputFileName, String outputFileName) {
        this.addCommand(String.format(patternCommand, inputFileName, outputFileName));
    }
}