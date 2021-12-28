package com.example.telephony.repository;

import com.example.telephony.domain.CallersBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CallerBaseRepository extends JpaRepository<CallersBase, Long> {
    @Query("select cb from CallersBase cb where cb.isConfirmed = ?1")
    List<CallersBase> findAllByConfirmedIs(boolean confirmed);

    @Query("select cb from CallersBase cb where cb.isConfirmed = ?1 and cb.name like ?2")
    Page<CallersBase> findAllByConfirmedIs(boolean confirmed, String name, Pageable pageable);

    @Query("select cb from CallersBase cb where cb.isConfirmed = ?1 order by cb.variablesList.size")
    Page<CallersBase> findAllByConfirmedIsOrderByVariablesSize(boolean confirmed, Pageable pageable);

    @Query("select cb.isConfirmed from CallersBase cb where cb.id = ?1")
    boolean baseIsConfirmed(Long id);

    @Query("select cb.callers.size from CallersBase cb where cb.id = ?1")
    Integer getCountCallersByCallersBaseId(Long id);
}
