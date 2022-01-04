package com.example.telephony.dto.dialing.common;

import lombok.Data;

@Data
public class DialingProgressDto {
    private Integer countCallers;
    private Integer countEnd;
    private Integer percentEnd;
}
