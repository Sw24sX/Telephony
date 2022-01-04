package com.example.telephony.dto.dialing;

import com.example.telephony.dto.BaseDto;
import com.example.telephony.dto.CommonDto;
import com.example.telephony.enums.DialingStatus;
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
