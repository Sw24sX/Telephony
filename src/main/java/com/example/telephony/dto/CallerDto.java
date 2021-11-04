package com.example.telephony.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class CallerDto extends BaseDto{
    private String number;
    private Map<String, String> variables;
    private boolean isValid;
}
