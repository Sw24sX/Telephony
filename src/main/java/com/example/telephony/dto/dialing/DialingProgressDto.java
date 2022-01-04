package com.example.telephony.dto.dialing;

import lombok.Data;

@Data
public class DialingProgressDto {
    private Integer countCallers;
    private Integer countEnd;
    private Integer percentEnd;
}
