package com.example.telephony.dto.scenario;

import com.example.telephony.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.awt.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class ScenarioNodeDto extends BaseDto {
    private String type;
    private ScenarioNodeDataDto data;
    private Point position;
}
