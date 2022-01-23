package com.example.telephony.dto.caller.base;

import com.example.telephony.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CallerDto extends BaseDto {
    private List<CallerVariablesDto> variables;
    private Integer number;
    private boolean isValid;
}
