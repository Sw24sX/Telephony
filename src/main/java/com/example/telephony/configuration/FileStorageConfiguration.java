package com.example.telephony.configuration;

import com.example.telephony.service.file.FileStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class FileStorageConfiguration implements CommandLineRunner {
    private final FileStorageService fileStorageService;

    public FileStorageConfiguration(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @Override
    public void run(String... args) {
        fileStorageService.init();
    }
}
