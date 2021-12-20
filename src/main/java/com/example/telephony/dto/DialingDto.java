package com.example.telephony.dto;

import com.example.telephony.enums.DialingStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class DialingDto extends BaseDto {
    private Date startDate;
    private Long scenarioId;
    private Long callersBaseId;
    private DialingStatus status;
    private String name;
}
