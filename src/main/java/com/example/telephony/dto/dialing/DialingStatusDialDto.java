package com.example.telephony.dto.dialing;

import lombok.Data;

@Data
public class DialingStatusDialDto {
    private Integer inProgress;
    private Integer done;
    private Integer scenarioNotEnd;
    private Integer havenNotReached;
}
