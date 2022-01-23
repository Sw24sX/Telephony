package com.example.telephony.mapper.scneario;

import com.example.telephony.domain.scenario.ScenarioHeader;
import com.example.telephony.dto.scenario.ScenarioHeaderDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScenarioHeaderMapper {
    ScenarioHeaderDto fromScenarioHeader(ScenarioHeader scenarioHeader);
}
