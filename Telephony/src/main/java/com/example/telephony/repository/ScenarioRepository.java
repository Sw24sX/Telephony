package com.example.telephony.repository;

import com.example.telephony.domain.scenario.Scenario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScenarioRepository extends JpaRepository<Scenario, Long> {
}
