package com.example.telephony.mapper;

import com.example.telephony.domain.CallerVariable;
import com.example.telephony.dto.CallerVariablesDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CallerVariableMapper {
    public abstract CallerVariablesDto fromCallerVariable(CallerVariable callerVariable);

    public abstract List<CallerVariablesDto> fromCallerVariable(List<CallerVariable> callerVariable);
}
