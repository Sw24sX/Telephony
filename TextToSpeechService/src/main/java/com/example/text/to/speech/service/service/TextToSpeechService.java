package com.example.text.to.speech.service.service;

import com.example.text.to.speech.service.dto.CreatedAudioFileResponse;
import com.example.text.to.speech.service.dto.SpeechTextRequest;
import com.example.text.to.speech.service.exception.TextToSpeechException;
import com.example.text.to.speech.service.service.command.line.CommandLineExecutor;
import com.example.text.to.speech.service.service.command.line.SoxCommands;
import com.google.cloud.texttospeech.v1.*;
import com.google.common.io.Files;
import com.google.protobuf.ByteString;
import ie.corballis.sox.Sox;
import ie.corballis.sox.WrongParametersException;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class TextToSpeechService {
    public List<CreatedAudioFileResponse> textToSpeechList(List<SpeechTextRequest> requests) {
        return requests.stream().map(this::textToSpeech).collect(Collectors.toList());
    }

    public CreatedAudioFileResponse textToSpeech(SpeechTextRequest request) {
        CreatedAudioFileResponse result = new CreatedAudioFileResponse(request);

        GoogleCloudTTS tts = new GoogleCloudTTS();
        ByteString audioContents = tts.textToSpeech(request);
        String fileName =  writeOutputFile(audioContents);
        result.setName(fileName);
        result.setUri(Paths.get(fileName).toAbsolutePath().toString());

        reformatFile(fileName);

        return result;
    }

    private String writeOutputFile(ByteString audioContents) {
        String fileName = generateNewFileName("raw");
        File resultFile = Paths.get(fileName).toFile();

        try {
            FileUtils.writeByteArrayToFile(resultFile, audioContents.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    private String reformatFile(String fileName) {
        String soxPath = Paths.get("sox-14-4-2\\sox.exe").toAbsolutePath().toString();
        Sox sox = new Sox(soxPath);
        String resultFileName = generateNewFileName("wav");
        try {
            sox
                .bits(16)
                .argument("-e", "signed-integer")
                .argument("-c", "1")
                .inputFile(fileName)
                .outputFile(resultFileName)
                .execute();
        } catch (IOException | WrongParametersException e ) {
            throw new TextToSpeechException(e.getMessage(), e.getCause());
        }
        return resultFileName;
    }

    private String generateNewFileName(String format) {
        return String.format("%s.%s", UUID.randomUUID(), format);
    }
}
