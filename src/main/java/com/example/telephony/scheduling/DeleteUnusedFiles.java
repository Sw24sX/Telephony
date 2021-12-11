package com.example.telephony.scheduling;

import com.example.telephony.common.Properties;
import com.example.telephony.enums.ExceptionMessage;
import com.example.telephony.exception.TelephonyException;
import com.example.telephony.repository.GeneratedSoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class DeleteUnusedFiles {
    private final Path generatedDirectory;
    private final GeneratedSoundRepository generatedSoundRepository;

    @Autowired
    public DeleteUnusedFiles(Environment environment, GeneratedSoundRepository generatedSoundRepository) {
        generatedDirectory = Paths.get(Properties.getProperty(environment, "file.generated.path"));
        this.generatedSoundRepository = generatedSoundRepository;
    }

    @Scheduled(cron = "0 0 6 * * *")
    public void deleteUnusedFiles() {
        for (File fileEntry : generatedDirectory.toFile().listFiles()) {
            if (fileEntry.isDirectory()) {
                throw new TelephonyException(ExceptionMessage.GENERATED_FOLDER_MUST_NOT_CONTAINS_FOLDER.getMessage());
            }

            String filePath = fileEntry.getPath();
            if (!generatedSoundRepository.existsByPath(filePath)) {
                if (fileEntry.delete()) {
                    System.out.println(String.format("File %s deleted", filePath));
                }
            }
        }
    }
}
