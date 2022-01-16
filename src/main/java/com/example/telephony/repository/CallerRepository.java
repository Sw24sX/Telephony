package com.example.telephony.repository;

import com.example.telephony.domain.callers.base.Caller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CallerRepository extends JpaRepository<Caller, Long> {
    @Query("select count(c) from Caller c where c.isValid = false and c.callersBase.id  = ?1")
    int getCountInvalidCallers(Long callersBaseId);

    @Query("select c from Caller c where c.callersBase.id = ?1 and c.isValid = ?2")
    Page<Caller> findAllByCallersBase_idAndAndValid(Long callersBase_id, boolean isValid, Pageable pageable);

    @Query("select c from Caller c where c.callersBase.id = ?1")
    Page<Caller> findAllByCallersBase_id(Long callersBase_id, Pageable pageable);

    @Query("select c.value from CallerVariable c where c.caller.id = ?1 and c.isPhoneColumn = true")
    String getCallerNumber(Long caller_id);

    @Query("select d.caller from DialingCallerResult d where d.dialing.id = ?1 order by d.caller.number")
    Page<Caller> getCallersByDialingId(Long dialingId, Pageable pageable);

}
