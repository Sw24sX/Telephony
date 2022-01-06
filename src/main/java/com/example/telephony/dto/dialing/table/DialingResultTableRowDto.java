package com.example.telephony.dto.dialing.table;

import com.example.telephony.dto.caller.base.CallerVariablesDto;
import lombok.Data;

import java.util.List;

@Data
public class DialingResultTableRowDto {
    private Integer number;
    private List<CallerVariablesDto> original;
    private List<ExtraCallerVariablesDto> extra;
}
