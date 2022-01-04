package com.example.telephony.dto.dialing;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DialingScenarioDto {
    private Long scenarioId;
    private Integer countSteps;
    private String name;
}
