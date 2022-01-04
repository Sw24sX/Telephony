package com.example.telephony.repository;

import com.example.telephony.domain.dialing.DialingCallerResult;
import com.example.telephony.enums.DialCallerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DialingCallerResultRepository extends JpaRepository<DialingCallerResult, Long> {
    @Query("select count(d) from DialingCallerResult d where d.dialing.id = ?1")
    Integer getCountDialingCallers(Long dialingId);

    @Query("select count(d) from DialingCallerResult d where d.dialing.id = ?1 and d.status = ?2")
    Integer getCountDialingCallersByStatus(Long dialingId, DialCallerStatus status);
}