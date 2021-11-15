package com.example.telephony.mapper;

import com.example.telephony.domain.Caller;
import com.example.telephony.domain.CallersBase;
import com.example.telephony.dto.CallerDto;
import com.example.telephony.dto.CallersBaseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CallerMapper.class})
public interface CallersBaseMapper {
//    @Mapping(target = "callers", source = "callers", resultType = CallerDto.class)
//    CallersBaseDto callersBaseToCallersBaseDto(CallersBase callersBase);

//    @Mapping(target = "callers", source = "callers", resultType = Caller.class)
//    CallersBase callersBaseDtoToCallersBase(CallersBaseDto callersBaseDto);

//    List<CallersBaseDto> listCallersBaseToListCallersBaseDto(List<CallersBase> callersBases);
}
