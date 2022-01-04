package com.example.telephony.mapper.dialing;

import com.example.telephony.domain.callers.base.CallersBase;
import com.example.telephony.domain.dialing.Dialing;
import com.example.telephony.domain.scenario.Scenario;
import com.example.telephony.dto.CommonDto;
import com.example.telephony.dto.dialing.*;
import com.example.telephony.service.CallerBaseService;
import com.example.telephony.service.DialingService;
import com.example.telephony.service.ScenarioService;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class DialingResultMapper {
    @Autowired
    protected ScenarioService scenarioService;

    @Autowired
    protected CallerBaseService callerBaseService;

    @Autowired
    protected DialingService dialingService;

    public DialingResultDto fromDialing(Dialing dialing) {
        if (dialing == null) {
            return null;
        }

        DialingResultDto dto = new DialingResultDto();
        dto.setId(dialing.getId());
        dto.setCreated(dialing.getCreated());
        dto.setName(dialing.getName());

        // todo not optimal
        CallersBase callersBase = callerBaseService.getById(dialing.getCallersBaseId());
        dto.setCallersBase(new DialingCallersBaseDto(callersBase.getId(), callersBase.getName(), callersBase.getCallers().size()));

        Scenario scenario = dialing.getScenario();
        dto.setScenario(new DialingScenarioDto(scenario.getId(), scenario.getCountSteps(), scenario.getName()));
        dto.setStatus(dialing.getStatus());
        dto.setStartDate(dialing.getStartDate());

        if (dialing.getStatistic() != null) {
            dto.setStartDialing(dialing.getStatistic().getStartDate());
            dto.setStartDialing(dialing.getStatistic().getEndDate());
        } else {
            dto.setStartDialing(dialing.getStartDate());
        }
        dto.setProgress(createProgress(dialing));

        return dto;
    }

    private DialingProgressDto createProgress(Dialing dialing) {
        DialingProgressDto result = new DialingProgressDto();

        result.setCountCallers(dialingService.getCountDialingCallers(dialing));
        result.setCountEnd(dialingService.getCountEndDialingCallers(dialing));
        result.setPercentEnd(calculatePercent(result.getCountCallers(), result.getCountEnd()));
        return result;
    }

    private Integer calculatePercent(Integer count, Integer percentCount) {
        return (percentCount * 100) / count;
    }
}
