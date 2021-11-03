package com.example.telephony.dto;

import lombok.Data;

import java.util.Map;

@Data
public class CallerDto {
    private Long id;
    private String number;
    private Map<String, String> variables;
    private boolean isValid;
}
