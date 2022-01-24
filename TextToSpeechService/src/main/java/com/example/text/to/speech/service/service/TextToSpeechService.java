package com.example.text.to.speech.service.service;

import com.example.text.to.speech.service.dto.CreatedAudioFileResponse;
import com.example.text.to.speech.service.dto.SpeechTextRequest;
import com.example.text.to.speech.service.service.command.line.CommandLineExecutor;
import com.example.text.to.speech.service.service.command.line.SoxCommands;
import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@Service
public class TextToSpeechService {
    public CreatedAudioFileResponse textToSpeech(SpeechTextRequest request) throws IOException {
        CreatedAudioFileResponse result = new CreatedAudioFileResponse(request);

        GoogleCloudTTS tts = new GoogleCloudTTS();
        ByteString audioContents = tts.textToSpeech(request);
        String fileName = writeOutputFile(audioContents);

        SoxCommands sox = new SoxCommands();
        sox.addCommand(fileName, generateNewFileName("wav"));

        CommandLineExecutor executor = new CommandLineExecutor();
        executor.addCommands(sox);
        executor.execute();

        return result;
    }

    private String writeOutputFile(ByteString audioContents) throws IOException {
        String fileName = generateNewFileName("mp3");
        try (OutputStream out = new FileOutputStream(fileName)) {
            out.write(audioContents.toByteArray());
            // TODO: 23.01.2022 log this
            System.out.printf("Audio content written to file \"%s\"", fileName);
        }

        return fileName;
    }

    private String generateNewFileName(String format) {
        return String.format("%s.%s", UUID.randomUUID(), format);
    }
}
