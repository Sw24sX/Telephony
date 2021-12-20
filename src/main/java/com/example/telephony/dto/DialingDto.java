package com.example.telephony.dto;

import com.example.telephony.enums.DialingStatus;
import lombok.Data;

import java.util.Date;

@Data
public class DialingDto extends BaseDto {
    private Date startDialing;
    private Long scenarioId;
    private Long callersBaseId;
    private DialingStatus status;
}
