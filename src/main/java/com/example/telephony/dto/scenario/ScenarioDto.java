package com.example.telephony.dto.scenario;

import com.example.telephony.dto.BaseDto;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class ScenarioDto extends BaseDto {
    private String name;
    private List<ScenarioNodeDto> nodes;
    private List<ScenarioEdgeDto> edges;
    private String rootId;
    private Long connectedCallerBaseId;
}
