package com.example.telephony.service.file;

import com.example.telephony.common.Properties;
import com.example.telephony.enums.exception.messages.ExceptionMessage;
import com.example.telephony.exception.TelephonyException;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class FileStorageService {
    private final Path uploadFiles;
    private final Path generatedFiles;

    public FileStorageService(Environment environment) {
        uploadFiles = Paths.get(Properties.getProperty(environment, "file.storage.path"));
        generatedFiles = Paths.get(Properties.getProperty(environment, "file.generated.path"));
    }

    public void init() {
        try {
            createDirectoriesIfNotExist();
        } catch (IOException e) {
            throw new TelephonyException(ExceptionMessage.NOT_INITIALIZE_FOLDER_FOR_UPLOAD.getMessage());
        }
    }

    private void createDirectoriesIfNotExist() throws IOException {
        if(!Files.exists(uploadFiles.toAbsolutePath())) {
            Files.createDirectories(uploadFiles.toAbsolutePath());
        }

        if (!Files.exists(generatedFiles.toAbsolutePath())) {
            Files.createDirectories(generatedFiles.toAbsolutePath());
        }
    }

    public String saveUpload(MultipartFile multipartFile, String fileName) {
        Path filePath = uploadFiles.resolve(fileName);
        try {
            multipartFile.transferTo(filePath);
        } catch (IOException e) {
            throw new TelephonyException(String.format(ExceptionMessage.COULD_NOT_STORE_FILE.getMessage(), fileName));
        }

        return filePath.toString();
    }
}
