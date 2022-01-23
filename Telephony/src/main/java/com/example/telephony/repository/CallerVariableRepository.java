package com.example.telephony.repository;

import com.example.telephony.domain.callers.base.CallerVariable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallerVariableRepository extends JpaRepository<CallerVariable, Long> {
}
