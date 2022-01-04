package com.example.telephony.dto.dialing.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DialingScenarioDto {
    private Long scenarioId;
    private Integer countSteps;
    private String name;
}
