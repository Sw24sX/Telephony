package com.example.telephony.dto.dialing.charts.pie;

import lombok.Data;

import java.util.List;

@Data
public class DialingResultPieChartDto {
    private List<DialingResultPartPieChartDto> parts;
    private Integer percentSuccess;
    private Integer countCallers;
    private Integer countSuccess;
}
