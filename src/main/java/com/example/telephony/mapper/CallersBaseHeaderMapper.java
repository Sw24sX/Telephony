package com.example.telephony.mapper;

import com.example.telephony.domain.CallersBase;
import com.example.telephony.dto.CallersBaseHeaderDto;
import com.example.telephony.repository.CallerRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public class CallersBaseHeaderMapper {
    @Autowired
    protected CallersBaseColumnMapper callersBaseColumnMapper;

    @Autowired
    protected CallerRepository callerRepository;

    public CallersBaseHeaderDto fromCallersBase(CallersBase callersBase) {
        if (callersBase == null) {
            return null;
        }

        CallersBaseHeaderDto result = new CallersBaseHeaderDto();
        result.setId(callersBase.getId());
        result.setCreated(callersBase.getCreated());
        result.setConfirmed(callersBase.isConfirmed());
        result.setCountCallers(callersBase.getCallers().size());
        result.setCountInvalidCallers(callerRepository.getCountInvalidCallers(callersBase.getId()));
        result.setColumns(callersBaseColumnMapper.fromVariablesTypeName(callersBase.getVariablesList()));
        return result;
    }
}
