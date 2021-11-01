package com.example.telephony.repository;

import com.example.telephony.domain.CallersBase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallerBaseRepository extends JpaRepository<CallersBase, Long> {
}
