package com.example.telephony.dto.dialing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DialingStatusDto {
    private String name;
    private String message;
}
