package com.example.telephony.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ScenarioDto extends BaseDto {
    private String name;
    private List<ScenarioNodeDto> nodes;
    private List<ScenarioEdgeDto> edges;
    private Long rootId;
}
