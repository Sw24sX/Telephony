package com.example.telephony.service;

import com.example.telephony.common.Properties;
import com.example.telephony.domain.Sound;
import com.example.telephony.enums.ExceptionMessage;
import com.example.telephony.exception.EntityNotFoundException;
import com.example.telephony.exception.TelephonyException;
import com.example.telephony.repository.SoundRepository;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class SoundService {
    private final SoundRepository soundRepository;
    private final FileStorageService fileStorageService;
    private final Environment environment;

    public SoundService(SoundRepository soundRepository, FileStorageService fileStorageService, Environment environment) {
        this.soundRepository = soundRepository;
        this.fileStorageService = fileStorageService;
        this.environment = environment;
    }

    public Sound getById(Long id) {
        return soundRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException(String.format(ExceptionMessage.SOUND_NOT_FOUND.getMessage(), id)));
    }

    public List<Sound> getAll() {
        return soundRepository.findAll();
    }

    public Sound create(MultipartFile multipartFile) {
        try {
            return createFile(multipartFile);
        } catch (IOException e) {
            throw new TelephonyException(e.getMessage());
        }
    }

    //TODO: resolve problem with unique name files
    private Sound createFile(MultipartFile multipartFile) throws IOException {
        String originalFileName = getFileName(multipartFile);
//        String uniqueFileName = UUID.randomUUID().toString();
        String pathToFile = fileStorageService.save(multipartFile, originalFileName);
        Sound sound = new Sound();
        sound.setName(originalFileName);
        sound.setPath(pathToFile);
        sound.setUri(buildSoundUri(originalFileName));
        return soundRepository.save(sound);
    }

    private String getFileName(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        if (fileName == null) {
            throw new TelephonyException(ExceptionMessage.FILE_NAME_IS_NULL.getMessage());
        }
        return fileName;
    }

    private String buildSoundUri(String soundName) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(Properties.getProperty(environment, "server.address"))
                .port(Properties.getProperty(environment, "server.port"))
                .path(Properties.getProperty(environment, "file.storage.url") + soundName).build();
        return uriComponents.toString();
    }

    public Sound update(Long id, Sound sound) {
        sound.setId(id);
        return soundRepository.save(sound);
    }

    public void delete(Long id) {
        Sound sound = getById(id);
        soundRepository.delete(sound);
    }
}
