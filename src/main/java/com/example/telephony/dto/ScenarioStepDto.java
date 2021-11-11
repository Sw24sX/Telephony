package com.example.telephony.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ScenarioStepDto extends BaseDto {
    private String question;
    private String soundPath;
    private Boolean isPositive;
}
