package com.example.telephony.dto.dialing;

import com.example.telephony.dto.CommonDto;
import lombok.Data;

import java.util.Date;

@Data
public class DialingResultDto {
    private Integer percentProgress;
    private Date startDate;
    private Date endDate;
    private CommonDto scenario;
    private CommonDto callersBase;
    private DialingStatusDialDto dialingStatusDial;
}
