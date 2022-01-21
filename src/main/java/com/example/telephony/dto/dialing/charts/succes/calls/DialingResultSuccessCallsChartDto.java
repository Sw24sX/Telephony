package com.example.telephony.dto.dialing.charts.succes.calls;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DialingResultSuccessCallsChartDto {
    private Integer successCalls;
    private LocalTime startTime;
    private String stringTime;
}
