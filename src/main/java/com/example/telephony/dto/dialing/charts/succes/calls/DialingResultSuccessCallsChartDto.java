package com.example.telephony.dto.dialing.charts.succes.calls;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class DialingResultSuccessCallsChartDto {
    private Integer successCalls;
    private Date date;
}
