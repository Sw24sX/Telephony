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
            "\t ds.id, \n" +
            "\t ds.creation_date, \n" +
            "\tds.caller_id,\n" +
            "\tds.is_hold_on,\n" +
            "\tds.answers,\n" +
            "\tds.dialing_id,\n" +
            "\tds.message_hold_on,\n" +
            "\tds.status_code\n" +
            "from (\n" +
                    "\t select \n" +
                    "\t\t id,\n" +
                    "\t\t creation_date,\n" +
                    "\t\t caller_id,\n" +
                    "\t\t is_hold_on,\n" +
                    "\t\t answers,\n" +
                    "\t\t dialing_id,\n" +
                    "\t\t message_hold_on,\n" +
                    "\t\t status_code,\n" +
                    "\t\t extract(hour from dcr.creation_date) * 60 * 60 * 1000 + \n" +
                    "\t\t extract(MINUTE  from dcr.creation_date) * 60 * 1000 +\n" +
                    "\t\t extract(MILLISECONDS from dcr.creation_date) as millsSum\n" +
                "\t from dialing_caller_result dcr\n" +
                "\t where (?1 is null) or (dialing_id = ?1) \n" +
                "\t order by millsSum\n" +
            ") as ds;", nativeQuery = true)
    List<DialingCallerResult> getDialingResultsByDialingOrderByMillsOfDay(Long dialingId);

    @Query(value = "select \n" +
            "\t ds.id, \n" +
            "\t ds.creation_date, \n" +
            "\tds.caller_id,\n" +
            "\tds.is_hold_on,\n" +
            "\tds.answers,\n" +
            "\tds.dialing_id,\n" +
            "\tds.message_hold_on,\n" +
            "\tds.status_code\n" +
            "from (\n" +
            "\t select \n" +
            "\t\t id,\n" +
            "\t\t creation_date,\n" +
            "\t\t caller_id,\n" +
            "\t\t is_hold_on,\n" +
            "\t\t answers,\n" +
            "\t\t dialing_id,\n" +
            "\t\t message_hold_on,\n" +
            "\t\t status_code,\n" +
            "\t\t extract(hour from dcr.creation_date) * 60 * 60 * 1000 + \n" +
            "\t\t extract(MINUTE  from dcr.creation_date) * 60 * 1000 +\n" +
            "\t\t extract(MILLISECONDS from dcr.creation_date) as millsSum\n" +
            "\t from dialing_caller_result dcr\n" +
            "\t order by millsSum\n" +
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