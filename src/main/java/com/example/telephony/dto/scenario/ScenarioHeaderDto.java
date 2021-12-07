package com.example.telephony.dto.scenario;

import com.example.telephony.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ScenarioHeaderDto extends BaseDto {
    private String name;
    private String countSteps;
}
