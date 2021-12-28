package com.example.telephony.repository;

import com.example.telephony.domain.Dialing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DialingRepository extends JpaRepository<Dialing, Long> {
    Page<Dialing> findAll(Pageable pageable);
}