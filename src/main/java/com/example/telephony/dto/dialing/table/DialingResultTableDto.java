package com.example.telephony.dto.dialing.table;

import com.example.telephony.dto.CallersBaseColumnDto;
import lombok.Data;

import java.util.List;

@Data
public class DialingResultTableDto {
    private List<CallersBaseColumnDto> original;
    private List<ExtraCallerBaseColumnDto> extra;
}
