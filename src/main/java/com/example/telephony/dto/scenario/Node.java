package com.example.telephony.dto.scenario;

import com.example.telephony.enums.ScenarioNodeTypes;
import lombok.Data;

@Data
public class Node {
    private int id;
    private ScenarioNodeTypes type;
    private String data;
}
