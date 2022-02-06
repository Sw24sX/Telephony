package com.example.telephony.tts.service.engine;

import com.example.telephony.common.PropertiesHelper;
import com.example.telephony.tts.common.SpeechFileHelper;
import com.example.telephony.enums.FilesFormat;
import com.example.telephony.tts.common.TTSPropertiesHelper;
import com.example.telephony.tts.exception.TextToSpeechException;
import com.example.telephony.tts.persistance.enums.TTSProperty;
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
        this.sox = new Sox(getSoxPath(environment));
        this.environment = environment;
    }

    private static String getSoxPath(Environment environment) {
        String basePath = TTSPropertiesHelper.getProperty(TTSProperty.SOX_PATH, environment);
        String systemPropertyName = TTSPropertiesHelper.getProperty(TTSProperty.SOX_ENV_PATH, environment);
        String systemPath = TTSPropertiesHelper.getSystemProperty(systemPropertyName);

        return systemPath == null ?
                Paths.get(basePath).toAbsolutePath().toString() :
                Paths.get(systemPath).toAbsolutePath().toString();
    }

    /**
     * Change file to 'asterisk'-format
     * @param inputFile File before reformatting
     * @return File after reformatting
     */
    public File reformatFile(File inputFile) {
        String filePath = TTSPropertiesHelper.getProperty( TTSProperty.TTS_RESULT_FILE, environment);
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
