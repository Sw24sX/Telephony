package com.example.telephony.dialing.mapper;

import com.example.telephony.domain.callers.base.CallersBase;
import com.example.telephony.dialing.persistance.model.Dialing;
import com.example.telephony.domain.scenario.Scenario;
import com.example.telephony.dialing.dto.common.DialingCallersBaseDto;
import com.example.telephony.dialing.dto.common.DialingProgressDto;
import com.example.telephony.dialing.dto.common.DialingResultDto;
import com.example.telephony.dialing.dto.common.DialingScenarioDto;
import com.example.telephony.service.CallerBaseService;
import com.example.telephony.dialing.service.DialingService;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public class DialingResultMapper {
    @Autowired
    private CallerBaseService callerBaseService;

    @Autowired
    private DialingService dialingService;

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
            dto.setEndDialing(dialing.getStatistic().getEndDate());
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
