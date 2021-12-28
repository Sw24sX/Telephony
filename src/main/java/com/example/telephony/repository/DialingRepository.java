package com.example.telephony.repository;

import com.example.telephony.domain.Dialing;
import com.example.telephony.enums.DialingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DialingRepository extends JpaRepository<Dialing, Long> {
    @Query("select d from Dialing d where d.name like ?1 and d.status = ?2")
    Page<Dialing> findAll(String name, DialingStatus status, Pageable pageable);
}