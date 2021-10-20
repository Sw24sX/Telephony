package com.example.telephony.repository;

import com.example.telephony.domain.Dial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DialRepository extends JpaRepository<Dial, Long> {
}
