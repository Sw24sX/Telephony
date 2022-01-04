package com.example.telephony.repository;

import com.example.telephony.domain.dialing.DialingStatistic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DialingStatisticRepository extends JpaRepository<DialingStatistic, Long> {
}