package com.example.telephony.repository;

import com.example.telephony.domain.VariablesTypeName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VariablesTypeNameRepository extends JpaRepository<VariablesTypeName, Long> {
    @Query("select vt.currentName from VariablesTypeName vt where vt.callersBase.id = ?1")
    List<String> getListColumnsName(Long callerBaseId);
}
