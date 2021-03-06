package com.example.telephony.repository;

import com.example.telephony.domain.dialing.Dialing;
import com.example.telephony.domain.dialing.DialingCallerResult;
import com.example.telephony.enums.DialCallerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface DialingCallerResultRepository extends JpaRepository<DialingCallerResult, Long> {
    @Query("select count(d) from DialingCallerResult d where d.dialing.id = ?1")
    Integer getCountDialingCallers(Long dialingId);

    @Query("select count(d) from DialingCallerResult d where d.dialing.id = ?1 and d.status = ?2")
    Integer getCountDialingCallersByStatus(Long dialingId, DialCallerStatus status);

    @Query("select count(d) from DialingCallerResult d where d.status = ?1")
    Integer getCountCallersByStatus(DialCallerStatus status);

    @Query("select d from DialingCallerResult d where d.dialing.id = ?1 and d.caller.id = ?2")
    Optional<DialingCallerResult> getDialingCallerResultByDialingIdAndCallerId(Long dialingId, Long callerId);

    @Query("select d.startCall from DialingCallerResult d where d.status = :status order by d.startCall")
    List<LocalTime> findAllStartCallTime(DialCallerStatus status);

    @Query("select d.startCall from DialingCallerResult d where d.dialing.id = :dialingId and d.status = :status order by d.startCall")
    List<LocalTime> findAllStartCallTimeByDialingId(Long dialingId, DialCallerStatus status);

    @Query(value = "select " +
                "sum(cl.countCallers) / count(cl.countCallers) " +
            "from (" +
            "select " +
                "count(*) as countCallers " +
                "from dialing_caller_result dcr " +
            "group by dialing_id " +
            ") as cl;", nativeQuery = true)
    Integer getAverageCountCallers();

    @Query(value = "select " +
                        "extract(EPOCH from sum(end_call_from_start_day - start_call_from_start_day) / count(*) ) " +
                    "from dialing_caller_result dcr where dcr.is_hold_on = false", nativeQuery = true)
    Double getAverageCallDuration();

    List<DialingCallerResult> findAllByDialing(Dialing dialing);
}