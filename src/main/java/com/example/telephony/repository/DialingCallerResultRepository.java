package com.example.telephony.repository;

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

    @Query("select d from DialingCallerResult d where d.dialing.id = ?1 and d.caller.id = ?2")
    Optional<DialingCallerResult> getDialingCallerResultByDialingIdAndCallerId(Long dialingId, Long callerId);

    @Query(value = "select \n" +
            "ds.id, \n" +
            "ds.creation_date, \n" +
            "ds.caller_id, \n" +
            "ds.is_hold_on, \n" +
            "ds.answers, \n" +
            "ds.dialing_id, \n" +
            "ds.message_hold_on, \n" +
            "ds.end_date, \n" +
            "ds.start_call, \n" +
            "ds.status_code \n" +
            "from ( \n" +
                "select \n" +
                    "id, \n" +
                    "creation_date, \n" +
                    "caller_id, \n" +
                    "is_hold_on, \n" +
                    "answers, \n" +
                    "dialing_id, \n" +
                    "message_hold_on, \n" +
                    "status_code, \n" +
                    "start_call, \n" +
                    "end_date, \n" +
                    "extract(hour from dcr.creation_date) * 60 * 60 * 1000 + \n" +
                    "extract(MINUTE  from dcr.creation_date) * 60 * 1000 + \n" +
                    "extract(MILLISECONDS from dcr.creation_date) as millsSum \n" +
                "from dialing_caller_result dcr\n" +
                "where dialing_id = ?1 and is_hold_on = false \n" +
                "order by millsSum \n" +
            ") as ds;", nativeQuery = true)
    List<DialingCallerResult> getDialingResultsByDialingOrderByMillsOfDay(Long dialingId);

    @Query(value = "" +
            "select \n" +
                "ds.id, \n" +
                "ds.creation_date, \n" +
                "ds.caller_id, \n" +
                "ds.is_hold_on, \n" +
                "ds.answers, \n" +
                "ds.dialing_id, \n" +
                "ds.message_hold_on, \n" +
                "ds.end_date, \n" +
                "ds.start_call, \n" +
                "ds.status_code \n" +
            "from ( \n" +
                "select \n" +
                    "id, \n" +
                    "creation_date, \n" +
                    "caller_id, \n" +
                    "is_hold_on, \n" +
                    "answers, \n" +
                    "dialing_id, \n" +
                    "message_hold_on, \n" +
                    "status_code, \n" +
                    "start_call, \n" +
                    "end_date, \n" +
                    "extract(hour from dcr.creation_date) * 60 * 60 * 1000 + \n" +
                    "extract(MINUTE  from dcr.creation_date) * 60 * 1000 + \n" +
                    "extract(MILLISECONDS from dcr.creation_date) as millsSum \n" +
                "from dialing_caller_result dcr\n" +
                "where is_hold_on = false \n" +
                "order by millsSum\n" +
            ") as ds;", nativeQuery = true)
    List<DialingCallerResult> getDialingResultsOrderByMillsOfDay();


    @Query(value = "select \n" +
                "sum(cl.countCallers) / count(cl.countCallers)" +
            "from (\n" +
            "select \n" +
                "count(*) as countCallers\n" +
                "from dialing_caller_result dcr\n" +
            "group by dialing_id \n" +
            ") as cl;", nativeQuery = true)
    Integer getAverageCountCallers();

    @Query(value = "select \n" +
                "cast ( extract(EPOCH FROM (sum(end_date - start_call) / count(*))) * 1000 as text)\n" +
            "from dialing_caller_result dcr\n" +
            "where is_hold_on = false;", nativeQuery = true)
    Double getAverageCallDuration();
}