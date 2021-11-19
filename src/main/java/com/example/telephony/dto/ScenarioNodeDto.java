package com.example.telephony.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ScenarioNodeDto extends BaseDto {
    private String type;
    private ScenarioNodeDataDto data;
}
