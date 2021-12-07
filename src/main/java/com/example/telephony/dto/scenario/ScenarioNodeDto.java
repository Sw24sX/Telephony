package com.example.telephony.dto.scenario;

import com.example.telephony.enums.ScenarioNodeTypes;
import lombok.Data;

import java.awt.*;

@Data
public class ScenarioNodeDto {
    private ScenarioNodeTypes type;
    private ScenarioNodeDataDto data;
    private Point position;
    private String id;
}
