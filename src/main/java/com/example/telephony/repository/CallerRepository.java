package com.example.telephony.repository;

import com.example.telephony.domain.Caller;
import org.aspectj.weaver.ast.Call;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface CallerRepository extends JpaRepository<Caller, Long> {
//    @Query(value = "select count(*) from (\n" +
//            "\tselect \n" +
//            "\t\tc.id\n" +
//            "\tfrom caller c\n" +
//            "\t\tright join caller_variables cv on cv.caller_id = c.id\n" +
//            "\t\twhere callers_base_id = 4\n" +
//            "\t\tgroup by c.id\n" +
//            "\t\thaving not bool_and(cv.is_valid)\n" +
//            ") as inv;",
//            nativeQuery = true)
    @Query(value = "select count(c) from Caller c where c.isValid = false and c.callersBase.id  = ?1")
    int getCountInvalidCallers(Long callersBaseId);

    @Query("select c from Caller c where c.callersBase.id = ?1 and c.isValid = ?2")
    Page<Caller> findAllByCallersBase_idAndAndValid(Long callersBase_id, boolean isValid, Pageable pageable);

    @Query("select cb from CallersBase cb order by cb.variablesList.size")
    List<Caller> findAllByCallersBase_id(Long callersBase_id);

    @Query("select c.value from CallerVariable c where c.caller.id = ?1 and c.isPhoneColumn = true")
    String getCallerNumber(Long caller_id);
}
