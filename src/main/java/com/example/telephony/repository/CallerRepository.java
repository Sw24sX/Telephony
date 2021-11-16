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

    @Query(value = "select count(*) from (\n" +
            "\tselect \n" +
            "\t\tc.id\n" +
            "\tfrom caller c\n" +
            "\t\tright join caller_variables cv on cv.caller_id = c.id\n" +
            "\t\twhere callers_base_id = 4\n" +
            "\t\tgroup by c.id\n" +
            "\t\thaving not bool_and(cv.is_valid)\n" +
            ") as inv;",
            nativeQuery = true)
    int getCountInvalidCallers(Long callersBaseId);
}
