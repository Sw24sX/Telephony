package com.example.telephony.dto.dialing.pie.chart;

import lombok.Data;

import java.util.List;

@Data
public class DialingResultPieChartDto {
    private List<DialingResultPartPieChartDto> parts;
    private Integer percentSuccess;
    private Integer countCallers;
    private Integer countSuccess;
}
