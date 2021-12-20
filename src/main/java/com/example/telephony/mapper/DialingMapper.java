package com.example.telephony.mapper;

import com.example.telephony.domain.Dialing;
import com.example.telephony.dto.DialingDto;
import com.example.telephony.service.DialingService;
import com.example.telephony.service.ScenarioService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class DialingMapper {
    @Autowired
    protected ScenarioService scenarioService;

    @Mappings({
            @Mapping(target = "scenario", expression = "java(scenarioService.getById(dto.getScenarioId()))"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "callersBaseId", source = "callersBaseId")
    })
    public abstract Dialing fromDialingDto(DialingDto dto);

    @Mapping(target = "scenarioId", source = "scenario.id")
    public abstract DialingDto fromDialing(Dialing dialing);

    public abstract List<DialingDto> fromListDialing(List<Dialing> dialings);
}
