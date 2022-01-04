package com.example.telephony.mapper;

import com.example.telephony.domain.callers.base.Caller;
import com.example.telephony.dto.CallerDto;
import com.example.telephony.dto.CallerVariablesDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {CallerVariableMapper.class})
public interface CallerMapper {
    @Mappings({
            @Mapping(target = "variables", source = "variables", resultType = CallerVariablesDto.class)
    })
    CallerDto fromCaller(Caller callers);
}
