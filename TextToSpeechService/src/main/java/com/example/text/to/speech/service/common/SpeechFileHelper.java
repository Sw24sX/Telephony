package com.example.text.to.speech.service.common;

import com.example.text.to.speech.service.enums.FilesFormat;
import com.example.text.to.speech.service.exception.TextToSpeechException;
import com.google.protobuf.ByteString;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class SpeechFileHelper {
    private SpeechFileHelper() {
    }

    public static File writeOutputFile(ByteString audioContents) {
        File resultFile = generateNewFileName(FilesFormat.RAW);

        try {
            FileUtils.writeByteArrayToFile(resultFile, audioContents.toByteArray());
        } catch (IOException e) {
            throw new TextToSpeechException(e.getMessage(), e.getCause());
        }

        return resultFile;
    }


    public static File generateNewFileName(String format) {
        return new File(String.format("%s.%s", UUID.randomUUID(), format));
    }

    public static File generateNewFileName(FilesFormat format) {
        return generateNewFileName(format.getFormat());
    }
}
