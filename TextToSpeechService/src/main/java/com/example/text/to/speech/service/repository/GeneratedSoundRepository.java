package com.example.text.to.speech.service.repository;

import com.example.text.to.speech.service.domain.GeneratedSound;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GeneratedSoundRepository extends JpaRepository<GeneratedSound, Long> {
    GeneratedSound findGeneratedSoundByText(String text);
}