package com.example.telephony.dto.scenario;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ScenarioNodeDataDto {
    private String replica;
    private Integer waitingTime;
    private List<ScenarioNodeAnswersDto> answers;
    private boolean needAnswer;
    private Date created;
}
