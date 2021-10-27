package com.example.telephony.service;

import com.example.telephony.common.Properties;
import com.example.telephony.exception.TelephonyException;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;


@Service
public class FileStorageService {
    private final Path path;

    public FileStorageService(Environment environment) {
        path = Paths.get(Properties.getProperty(environment, "file.storage.path"));
    }

    public void init() {
        try {
            createDirectoriesIfNotExist();
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    private void createDirectoriesIfNotExist() throws IOException {
        if(!Files.exists(path.toAbsolutePath())) {
            Files.createDirectories(path.toAbsolutePath());
        }
    }

    public void save(MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getOriginalFilename();
            multipartFile.transferTo(path.resolve(fileName));
        } catch (IOException e) {
            throw new TelephonyException("Could not store the file. Error:"+e.getMessage());
        }
    }

    public Resource load(String filename) {
        Path file = path.resolve(filename);
        try {
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            }else{
                throw new RuntimeException("Could not read the file.");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error:"+e.getMessage());
        }
    }

    public Stream<Path> load() {
        try {
            return Files.walk(this.path,1)
                    .filter(path -> !path.equals(this.path))
                    .map(this.path::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files.");
        }
    }
}
