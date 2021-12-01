package com.example.telephony.repository;

import com.example.telephony.domain.Sound;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoundRepository extends JpaRepository<Sound, Long> {
}
