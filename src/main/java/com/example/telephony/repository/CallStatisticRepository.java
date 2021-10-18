package com.example.telephony.repository;

import com.example.telephony.domain.CallStatistic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallStatisticRepository extends JpaRepository<CallStatistic, Long> {
    CallStatistic findByChannel(String channel);
}
