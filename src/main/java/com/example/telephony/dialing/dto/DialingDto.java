package com.example.telephony.dialing.dto;

import com.example.telephony.dto.BaseDto;
import com.example.telephony.dto.CommonDto;
import com.example.telephony.dialing.persistance.enums.DialingStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class DialingDto extends BaseDto {
    private Date startDate;
    private CommonDto scenario;
    private CommonDto callersBase;
    private DialingStatus status;
    private String name;
    private Integer percentEnd;
}
