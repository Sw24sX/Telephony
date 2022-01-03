package com.example.telephony.repository;

import com.example.telephony.domain.CallersBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CallerBaseRepository extends JpaRepository<CallersBase, Long> {
    @Query("select cb from CallersBase cb where cb.name like ?1")
    Page<CallersBase> findAllByNameLike(String name, Pageable pageable);

    @Query("select cb.callers.size from CallersBase cb where cb.id = ?1")
    Integer getCountCallersByCallersBaseId(Long id);
}
