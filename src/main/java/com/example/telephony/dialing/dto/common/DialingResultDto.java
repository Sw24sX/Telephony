package com.example.telephony.dialing.dto.common;

import com.example.telephony.dto.BaseDto;
import com.example.telephony.dialing.persistance.enums.DialingStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class DialingResultDto extends BaseDto {
    private Date startDate;
    private DialingScenarioDto scenario;
    private DialingCallersBaseDto callersBase;
    private DialingStatus status;
    private String name;
    private DialingProgressDto progress;
    private Date startDialing;
    private Date endDialing;
}
