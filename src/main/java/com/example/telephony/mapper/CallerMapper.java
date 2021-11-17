package com.example.telephony.mapper;

import com.example.telephony.domain.Caller;
import com.example.telephony.dto.CallerDto;
import com.example.telephony.dto.CallerVariablesDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CallerVariableMapper.class})
public interface CallerMapper {
    @Mappings({
            @Mapping(target = "variables", source = "variables", resultType = CallerVariablesDto.class)
    })
    public abstract CallerDto fromCaller(Caller callers);

    public abstract List<CallerDto> fromCaller(List<Caller> callers);
}
