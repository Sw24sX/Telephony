package com.example.telephony.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class CallerDto extends BaseDto{
    private List<CallerVariablesDto> variables;
    private Integer number;
    private boolean isValid;
}
