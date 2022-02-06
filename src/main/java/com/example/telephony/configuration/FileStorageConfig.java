package com.example.telephony.configuration;

import com.example.telephony.service.file.FileStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileStorageConfig implements CommandLineRunner {
    private final FileStorageService fileStorageService;

    public FileStorageConfig(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @Override
    public void run(String... args) {
        fileStorageService.init();
    }
}
