package com.example.telephony.repository;

import com.example.telephony.domain.scenario.ScenarioHeader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScenarioHeaderRepository extends JpaRepository<ScenarioHeader, Long> {
    @Query(value = "select s from ScenarioHeader s where lower(s.name) like lower(?1)")
    Page<ScenarioHeader> findAll(String name, Pageable pageable);
}
