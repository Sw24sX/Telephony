package com.example.telephony.repository;

import com.example.telephony.domain.dialing.DialingStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DialingStatisticRepository extends JpaRepository<DialingStatistic, Long> {
    @Query(value = "" +
            "select cast (extract(EPOCH FROM average) * 1000 as text) from (\n" +
                "select \n" +
                    "sum(end_date - start_date) / count(*) as average\n" +
                "from dialing_statistic ds\n" +
            ") as av;", nativeQuery = true)
    Double getAverageDialingDuration();

    @Query("select count(d) from DialingStatistic d")
    Integer getCountDialingsRun();
}