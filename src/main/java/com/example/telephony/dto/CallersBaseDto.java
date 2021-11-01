package com.example.telephony.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CallersBaseDto {
    private Long id;
    private String name;
    private List<CallerDto> callers;
}
