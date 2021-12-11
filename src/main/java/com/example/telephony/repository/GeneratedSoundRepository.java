package com.example.telephony.repository;

import com.example.telephony.domain.GeneratedSound;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneratedSoundRepository extends JpaRepository<GeneratedSound, Long> {
    boolean existsByPath(String path);
    void deleteByPath(String path);
}
