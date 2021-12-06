package com.example.telephony.dto.scenario;

import com.example.telephony.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ScenarioNodeDataDto extends BaseDto {
    private String replica;
    private Integer waitingTime;
    private List<ScenarioNodeAnswersDto> answers;
    private boolean needAnswer;
    private Date created;
}
