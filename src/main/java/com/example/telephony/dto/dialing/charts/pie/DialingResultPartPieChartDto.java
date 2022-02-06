package com.example.telephony.dto.dialing.charts.pie;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DialingResultPartPieChartDto {
    private String name;
    private Integer value;
    private String key;
}
