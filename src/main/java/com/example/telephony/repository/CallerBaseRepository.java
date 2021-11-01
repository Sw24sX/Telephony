package com.example.telephony.repository;

import com.example.telephony.domain.CallersBase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CallerBaseRepository extends JpaRepository<CallersBase, Long> {
}
