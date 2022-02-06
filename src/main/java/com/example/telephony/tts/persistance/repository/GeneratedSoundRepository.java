package com.example.telephony.tts.persistance.repository;

import com.example.telephony.tts.persistance.domain.GeneratedSound;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneratedSoundRepository extends JpaRepository<GeneratedSound, Long> {
    boolean existsByPath(String path);
    void deleteByPath(String path);
}
