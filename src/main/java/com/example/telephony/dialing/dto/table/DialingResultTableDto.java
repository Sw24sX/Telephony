package com.example.telephony.dialing.dto.table;

import com.example.telephony.dto.caller.base.CallersBaseColumnDto;
import lombok.Data;

import java.util.List;

@Data
public class DialingResultTableDto {
    private List<CallersBaseColumnDto> original;
    private List<ExtraCallerBaseColumnDto> extra;
}
