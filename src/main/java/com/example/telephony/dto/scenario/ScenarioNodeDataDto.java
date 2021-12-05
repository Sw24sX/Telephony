package com.example.telephony.dto.scenario;

import lombok.Data;

import java.util.Date;

@Data
public class ScenarioNodeDataDto {
    private String replica;
    private int waitingTime;
    private String answerKey;
    private boolean needAnswer;
    private Date created;
}
