package com.example.telephony.dto;

import lombok.Data;

@Data
public class ScenarioNodeDataDto {
    private String question;
    private String soundPath;
    private String answerKey;
    private boolean needAnswer;
}
