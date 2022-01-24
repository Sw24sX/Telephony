package com.example.text.to.speech.service.service.command.line;

import java.util.ArrayList;
import java.util.List;

public class CommandLineCommands {
    private final List<String> commands;

    public CommandLineCommands() {
        commands = new ArrayList<>();
    }

    protected void addCommand(String command) {
        commands.add(command);
    }

    public List<String> getCommands() {
        return commands;
    }
}
