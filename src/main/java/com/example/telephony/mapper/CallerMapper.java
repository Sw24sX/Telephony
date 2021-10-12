package com.example.telephony.mapper;

import com.example.telephony.domain.Caller;
import com.example.telephony.dto.CallerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CallerMapper {
    CallerDto callerToCallerDto(Caller caller);
    Caller callerToCallerDto(CallerDto callerDto);
}
