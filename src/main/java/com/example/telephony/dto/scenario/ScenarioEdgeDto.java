package com.example.telephony.dto.scenario;

import lombok.Data;

@Data
public class ScenarioEdgeDto {
    private String id;
    private Long source;
    private Long target;
    private String sourceHandler;
}
