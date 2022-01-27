package com.example.text.to.speech.service.common;

import com.example.text.to.speech.service.enums.FilesFormat;
import com.example.text.to.speech.service.exception.TextToSpeechException;
import com.google.protobuf.ByteString;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class SpeechFileHelper {
    private SpeechFileHelper() {
    }

    public static File writeOutputFile(ByteString audioContents, String path) {
        File resultFile = generateNewFile(FilesFormat.RAW, path);

        try {
            FileUtils.writeByteArrayToFile(resultFile, audioContents.toByteArray());
        } catch (IOException e) {
            throw new TextToSpeechException(e.getMessage(), e.getCause());
        }

        return resultFile;
    }


    public static File generateNewFile(String format, String path) {
        return new File(path, String.format("%s.%s", UUID.randomUUID(), format));
    }

    public static File generateNewFile(FilesFormat format, String path) {
        return generateNewFile(format.getFormat(), path);
    }

    public static void deleteTempFiles(String path) {
        try {
            FileUtils.cleanDirectory(new File(path));
        } catch (IOException e) {
            throw new TextToSpeechException(e.getMessage(), e.getCause());
        }
    }
}
