package com.example.telephony.repository;

import com.example.telephony.domain.Caller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface CallerRepository extends JpaRepository<Caller, Long> {
//    List<Caller> findAllByNumberIn(Collection<String> number);

//    @Query("select u from Caller u where u.number in :callers.number")
//    List<Caller> findAllByNumberIn(Collection<Caller> callers);
}
