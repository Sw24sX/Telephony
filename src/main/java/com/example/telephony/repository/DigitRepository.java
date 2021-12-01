package com.example.telephony.repository;

import com.example.telephony.domain.Digit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DigitRepository extends JpaRepository<Digit, Long> {
}
