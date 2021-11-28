package com.example.telephony.mapper;

import com.example.telephony.domain.CallersBase;
import com.example.telephony.dto.CallersBaseHeaderDto;
import com.example.telephony.service.CallerService;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CallersBaseHeaderMapper {
    @Autowired
    protected CallersBaseColumnMapper callersBaseColumnMapper;

    @Autowired
    protected CallerService callerService;

    public CallersBaseHeaderDto fromCallersBase(CallersBase callersBase) {
        if (callersBase == null) {
            return null;
        }

        CallersBaseHeaderDto result = new CallersBaseHeaderDto();
        result.setId(callersBase.getId());
        result.setCreated(callersBase.getCreated());
        result.setConfirmed(callersBase.isConfirmed());
        result.setCountCallers(callersBase.getCallers() == null ? 0 : callersBase.getCallers().size());
        result.setCountInvalidCallers(callerService.getCountInvalidCallers(callersBase.getId()));
        result.setColumns(callersBaseColumnMapper.fromVariablesTypeName(callersBase.getVariablesList()));
        result.setName(callersBase.getName());
        result.setUpdated(callersBase.getUpdated());
        return result;
    }

    public abstract List<CallersBaseHeaderDto> fromCallersBase(List<CallersBase> callersBase);

    public CallersBase fromCallersBaseHeaderDto(CallersBaseHeaderDto callersBaseHeaderDto) {
        if (callersBaseHeaderDto == null) {
            return null;
        }

        CallersBase callersBase = new CallersBase();
        callersBase.setName(callersBaseHeaderDto.getName());
        callersBase.setConfirmed(callersBaseHeaderDto.isConfirmed());
        callersBase.setVariablesList(callersBaseColumnMapper.fromCallersBaseColumnDto(callersBaseHeaderDto.getColumns()));
        return callersBase;
    }
}
