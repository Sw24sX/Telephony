package com.example.telephony.repository;

import com.example.telephony.domain.scenario.ScenarioNode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScenarioNodeRepository extends JpaRepository<ScenarioNode, Long> {
}
