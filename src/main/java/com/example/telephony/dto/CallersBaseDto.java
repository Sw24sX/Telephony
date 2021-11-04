package com.example.telephony.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CallersBaseDto extends BaseDto {
    private String name;
    private List<CallerDto> callers;
    private String[] variablesList;
}
