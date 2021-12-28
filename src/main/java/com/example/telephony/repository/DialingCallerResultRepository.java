package com.example.telephony.repository;

import com.example.telephony.domain.DialingCallerResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DialingCallerResultRepository extends JpaRepository<DialingCallerResult, Long> {
    @Query("select count(d) from DialingCallerResult d where d.dialing.id = ?1")
    Integer getCountDialingCallers(Long dialingId);
}