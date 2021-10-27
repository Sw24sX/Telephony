package com.example.telephony.configuration;

import com.example.telephony.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class FileUploadConfiguration implements CommandLineRunner {
    private final FileStorageService fileStorageService;

    public FileUploadConfiguration(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @Override
    public void run(String... args) {
//        fileStorageService.clear();
        fileStorageService.init();
    }
}
