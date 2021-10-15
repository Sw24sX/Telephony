package com.example.telephony.mapper;

import com.example.telephony.domain.Caller;
import com.example.telephony.domain.Dial;
import com.example.telephony.dto.CallerDto;
import com.example.telephony.dto.DialDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CallerMapper.class})
public interface DialMapper {
    @Mapping(target = "callers", source = "callers", resultType = CallerDto.class)
    DialDto dialToDialDto(Dial dial);

    @Mapping(target = "callers", source = "callers", resultType = Caller.class)
    Dial dialDtoToDial(DialDto dialDto);

    List<DialDto> listDialToListDialDto(List<Dial> dials);
}
