package com.example.telephony.repository;

import com.example.telephony.domain.CallersBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CallerBaseRepository extends JpaRepository<CallersBase, Long> {
    @Query("select cb from CallersBase cb where cb.isConfirmed = ?1")
    List<CallersBase> findAllByConfirmedIs(boolean confirmed);
}
