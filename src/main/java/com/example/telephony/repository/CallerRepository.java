package com.example.telephony.repository;

import com.example.telephony.domain.Caller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallerRepository extends JpaRepository<Caller, Long> {
}
