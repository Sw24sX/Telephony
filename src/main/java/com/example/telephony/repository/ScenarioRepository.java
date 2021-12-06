package com.example.telephony.repository;

import com.example.telephony.domain.scenario.Scenario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScenarioRepository extends JpaRepository<Scenario, Long> {
    @Query(value = "select s from Scenario s")
    Page<Scenario> findAll(Pageable pageable);
}
