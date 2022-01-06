package com.example.telephony.dto;

import com.example.telephony.common.CustomTime;
import lombok.Data;

@Data
public class CommonStatisticDto {
    private Integer totalDialings;
    private Integer averageNumberOfCallsPerDialing;
    private CustomTime averageDialingsDuration;
    private CustomTime averageCallDuration;
}
