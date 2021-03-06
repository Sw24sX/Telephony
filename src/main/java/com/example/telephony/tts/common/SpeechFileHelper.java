package com.example.telephony.tts.common;

import com.example.telephony.enums.FilesFormat;
import com.example.telephony.tts.exception.TextToSpeechException;
import com.google.protobuf.ByteString;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Common class for work with generated files
 */
public class SpeechFileHelper {
    private SpeechFileHelper() {
    }

    /**
     * Create .raw temp file
     * @param audioContents File content
     * @param path Path to file
     * @return New file
     */
    public static File writeTempFile(ByteString audioContents, String path) {
        File resultFile = generateNewFile(FilesFormat.RAW, path);

        try {
            FileUtils.writeByteArrayToFile(resultFile, audioContents.toByteArray());
        } catch (IOException e) {
            throw new TextToSpeechException(e.getMessage(), e.getCause());
        }

        return resultFile;
    }

    /**
     * Generate new file name
     * @param extension File extension
     * @param path Path to file
     * @return Generated file name
     */
    public static File generateNewFile(String extension, String path) {
        String newName = UUID.randomUUID().toString().replace("-", "");
        return new File(path, String.format("%s.%s", newName, extension));
    }

    /**
     * Generate new file name
     * @param format Enum with file extensions
     * @param path Path to file
     * @return Generated file name
     */
    public static File generateNewFile(FilesFormat format, String path) {
        return generateNewFile(format.getExtension(), path);
    }

    /**
     * Clear directory for temp files
     * @param path Path to directory with temp files
     */
    public static void deleteTempFiles(String path) {
        try {
            FileUtils.cleanDirectory(new File(path));
        } catch (IOException e) {
            throw new TextToSpeechException(e.getMessage(), e.getCause());
        }
    }
}
