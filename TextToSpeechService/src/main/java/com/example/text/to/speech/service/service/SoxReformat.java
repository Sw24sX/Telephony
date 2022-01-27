package com.example.text.to.speech.service.service;

import com.example.text.to.speech.service.common.PropertiesHelper;
import com.example.text.to.speech.service.common.SpeechFileHelper;
import com.example.text.to.speech.service.enums.CustomApplicationProperty;
import com.example.text.to.speech.service.enums.FilesFormat;
import com.example.text.to.speech.service.exception.TextToSpeechException;
import ie.corballis.sox.Sox;
import ie.corballis.sox.WrongParametersException;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Use sox utility
 */
public class SoxReformat {
    private final Sox sox;
    private final Environment environment;

    public SoxReformat(Environment environment) {
        createDirectoryIfNotExist(environment);
        this.sox = new Sox(getSoxPath(environment));
        this.environment = environment;
    }

    private static String getSoxPath(Environment environment) {
        String basePath = PropertiesHelper.getApplicationProperty(CustomApplicationProperty.SOX_PATH, environment);
        String systemPropertyName = PropertiesHelper.getApplicationProperty(CustomApplicationProperty.SOX_ENV_PATH, environment);
        String systemPath = PropertiesHelper.getSystemEnvironment(systemPropertyName);

        return systemPath == null ?
                Paths.get(basePath).toAbsolutePath().toString() :
                Paths.get(systemPath).toAbsolutePath().toString();
    }

    private static void createDirectoryIfNotExist(Environment environment) {
        String path = PropertiesHelper.getApplicationProperty(CustomApplicationProperty.TTS_RESULT_FILE, environment);
        new File(path).mkdirs();
    }

    /**
     * Change file to 'asterisk'-format
     * @param inputFile File before reformatting
     * @return File after reformatting
     */
    public File reformatFile(File inputFile) {
        String filePath = PropertiesHelper.getApplicationProperty(CustomApplicationProperty.TTS_RESULT_FILE, environment);
        File outputFile = SpeechFileHelper.generateNewFile(FilesFormat.WAV, filePath);
        try {
            sox
                    .bits(16)
                    .argument("-e", "signed-integer")
                    .argument("-c", "1")
                    .inputFile(inputFile.getAbsolutePath())
                    .outputFile(outputFile.getAbsolutePath())
                    .execute();
        } catch (IOException | WrongParametersException e ) {
            throw new TextToSpeechException(e.getMessage(), e.getCause());
        }
        return outputFile;
    }
}
