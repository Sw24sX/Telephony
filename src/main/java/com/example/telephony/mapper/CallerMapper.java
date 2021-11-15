package com.example.telephony.mapper;

import com.example.telephony.domain.Caller;
import com.example.telephony.dto.CallerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CallerMapper {
//    CallerDto callerToCallerDto(Caller caller);
//
//    @Mapping(target = "id", ignore = true)
//    Caller callerToCallerDto(CallerDto callerDto);
//
//    List<CallerDto> listCallerToCallerDto(List<Caller> callers);
//    List<Caller> listCallerDtoToCaller(List<CallerDto> callerDtos);
}
