package com.example.telephony.repository;

import com.example.telephony.domain.dialing.Dialing;
import com.example.telephony.domain.dialing.DialingCallerResult;
import com.example.telephony.enums.DialCallerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

    @Query(value = "select " +
            "ds.id, " +
            "ds.creation_date, " +
            "ds.caller_id, " +
            "ds.is_hold_on, " +
            "ds.answers, " +
            "ds.dialing_id, " +
            "ds.message_hold_on, " +
            "ds.end_date, " +
            "ds.start_call, " +
            "ds.status_code " +
            "from ( " +
                "select " +
                    "id, " +
                    "creation_date, " +
                    "caller_id, " +
                    "is_hold_on, " +
                    "answers, " +
                    "dialing_id, " +
                    "message_hold_on, " +
                    "status_code, " +
                    "start_call, " +
                    "end_date, " +
                    "extract(hour from dcr.creation_date) * 60 * 60 * 1000 + " +
                    "extract(MINUTE  from dcr.creation_date) * 60 * 1000 + " +
                    "extract(MILLISECONDS from dcr.creation_date) as millsSum " +
                "from dialing_caller_result dcr " +
                "where dialing_id = ?1 and is_hold_on = false " +
                "order by millsSum " +
            ") as ds;", nativeQuery = true)
    List<DialingCallerResult> getDialingResultsByDialingOrderByMillsOfDay(Long dialingId);

    @Query(value = "select " +
                "ds.id, " +
                "ds.creation_date, " +
                "ds.caller_id, " +
                "ds.is_hold_on, " +
                "ds.answers, " +
                "ds.dialing_id, " +
                "ds.message_hold_on, " +
                "ds.end_date, " +
                "ds.start_call, " +
                "ds.status_code " +
            "from ( " +
                "select " +
                    "id, " +
                    "creation_date, " +
                    "caller_id, " +
                    "is_hold_on, " +
                    "answers, " +
                    "dialing_id, " +
                    "message_hold_on, " +
                    "status_code, " +
                    "start_call, " +
                    "end_date, " +
                    "extract(hour from dcr.creation_date) * 60 * 60 * 1000 + " +
                    "extract(MINUTE  from dcr.creation_date) * 60 * 1000 + " +
                    "extract(MILLISECONDS from dcr.creation_date) as millsSum " +
                "from dialing_caller_result dcr " +
                "where is_hold_on = false " +
                "order by millsSum " +
            ") as ds;", nativeQuery = true)
    List<DialingCallerResult> getDialingResultsOrderByMillsOfDay();


    @Query(value = "select \n" +
                "sum(cl.countCallers) / count(cl.countCallers)" +
            "from (" +
            "select " +
                "count(*) as countCallers " +
                "from dialing_caller_result dcr " +
            "group by dialing_id " +
            ") as cl;", nativeQuery = true)
    Integer getAverageCountCallers();

    @Query(value = "select " +
                "cast ( extract(EPOCH FROM (sum(end_date - start_call) / count(*))) * 1000 as text) " +
            "from dialing_caller_result dcr " +
            "where is_hold_on = false;", nativeQuery = true)
    Double getAverageCallDuration();

    List<DialingCallerResult> findAllByDialing(Dialing dialing);
}