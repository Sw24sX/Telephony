package com.example.telephony.mapper;

import com.example.telephony.domain.CallersBase;
import com.example.telephony.domain.Dialing;
import com.example.telephony.domain.scenario.Scenario;
import com.example.telephony.dto.CommonDto;
import com.example.telephony.dto.DialingDto;
import com.example.telephony.service.CallerBaseService;
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

    @Autowired
    protected CallerBaseService callerBaseService;

    @Autowired
    protected DialingService dialingService;

    @Mappings({
            @Mapping(target = "scenario", expression = "java(scenarioService.getById(dto.getScenario().getId()))"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "callersBaseId", source = "callersBase.id")
    })
    public abstract Dialing fromDialingDto(DialingDto dto);

    public DialingDto fromDialing(Dialing dialing) {
        if (dialing == null) {
            return null;
        }

        DialingDto dto = new DialingDto();
        dto.setId(dialing.getId());
        dto.setCreated(dialing.getCreated());
        dto.setName(dialing.getName());

        CallersBase callersBase = callerBaseService.getById(dialing.getCallersBaseId());
        dto.setCallersBase(new CommonDto(callersBase.getId(), callersBase.getName()));

        dto.setScenario(new CommonDto(dialing.getScenario().getId(), dialing.getScenario().getName()));
        dto.setStatus(dialing.getStatus());
        dto.setStartDate(dialing.getStartDate());
        dto.setPercentEnd(calculatePercent(dialing));
        return dto;
    }

    private Integer calculatePercent(Dialing dialing) {
        Integer countEnd = dialingService.getCountEndDialingCallers(dialing);
        Integer count = dialingService.getCountDialingCallers(dialing);

        return (countEnd * 100) / count;
    }

    public abstract List<DialingDto> fromListDialing(List<Dialing> dialings);
}
