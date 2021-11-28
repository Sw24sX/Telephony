package com.example.telephony.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CallersBaseHeaderDto extends BaseDto {
    private boolean isConfirmed;
    private int countCallers;
    private int countInvalidCallers;
    private List<CallersBaseColumnDto> columns;
    private String name;
}
