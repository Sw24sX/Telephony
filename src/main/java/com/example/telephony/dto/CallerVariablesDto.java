package com.example.telephony.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CallerVariablesDto extends BaseDto {
    private String value;
    private boolean isValid;
    private boolean isPhoneColumn;
}
