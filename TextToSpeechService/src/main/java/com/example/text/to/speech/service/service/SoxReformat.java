package com.example.text.to.speech.service.service;

import com.example.text.to.speech.service.common.PropertiesHelper;
import com.example.text.to.speech.service.common.SpeechFileHelper;
import com.example.text.to.speech.service.enums.FilesFormat;
import com.example.text.to.speech.service.exception.TextToSpeechException;
import ie.corballis.sox.Sox;
import ie.corballis.sox.WrongParametersException;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SoxReformat {
    private final Sox sox;

    public SoxReformat(Environment environment) {
        this.sox = new Sox(getSoxPath(environment));
    }

    public File reformatFile(File inputFile) {
        File outputFile = SpeechFileHelper.generateNewFileName(FilesFormat.WAV);
        try {
            sox
                .bits(16)
                .argument("-e", "signed-integer")
                .argument("-c", "1")
                .inputFile(inputFile.getName())
                .outputFile(outputFile.getName())
                .execute();
        } catch (IOException | WrongParametersException e ) {
            throw new TextToSpeechException(e.getMessage(), e.getCause());
        }
        return outputFile;
    }

    private static String getSoxPath(Environment environment) {
        String basePath = PropertiesHelper.getApplicationProperty("sox.path", environment);
        String systemPropertyName = PropertiesHelper.getApplicationProperty("sox.env.path", environment);
        String systemPath = PropertiesHelper.geSystemEnvironment(systemPropertyName);

        return systemPath == null ?
                Paths.get(basePath).toAbsolutePath().toString() :
                Paths.get(systemPath).toAbsolutePath().toString();
    }
}
