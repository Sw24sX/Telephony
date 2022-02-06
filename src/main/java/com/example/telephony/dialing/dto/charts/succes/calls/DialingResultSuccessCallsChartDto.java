package com.example.telephony.dialing.dto.charts.succes.calls;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DialingResultSuccessCallsChartDto {
    private Integer successCalls;
    private LocalTime date;
    private String time;
}
