package com.example.telephony.dialing.persistance.repository;

import com.example.telephony.dialing.persistance.model.Dialing;
import com.example.telephony.dialing.persistance.enums.DialingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DialingRepository extends JpaRepository<Dialing, Long> {
    @Query("select d from Dialing d " +
                "where lower(d.name) like lower(?1) and d.status = ?2")
    Page<Dialing> findAllByNameAndStatus(String name, DialingStatus status, Pageable pageable);

    @Query("select d from Dialing d " +
            "where lower(d.name) like lower(?1) order by d.status")
    Page<Dialing> findAllByName(String name, Pageable pageable);

    List<Dialing> findAllByCallersBaseId(Long callersBaseId);

    List<Dialing> findAllByScenario_Id(Long scenarioId);
}