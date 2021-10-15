package com.example.telephony.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DialDto {
    private Long id;
    private LocalDateTime dialStart;
    private LocalDateTime dialEnd;
    private String name;
    private List<CallerDto> callers;
}
