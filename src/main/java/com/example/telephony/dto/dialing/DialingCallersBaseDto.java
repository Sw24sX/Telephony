package com.example.telephony.dto.dialing;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DialingCallersBaseDto {
    private Long id;
    private String name;
    private Integer countCallers;
}
