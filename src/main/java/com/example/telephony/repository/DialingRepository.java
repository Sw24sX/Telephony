package com.example.telephony.repository;

import com.example.telephony.domain.Dialing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DialingRepository extends JpaRepository<Dialing, Long> {
}