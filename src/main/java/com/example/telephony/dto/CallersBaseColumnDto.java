package com.example.telephony.dto;

import com.example.telephony.enums.VariablesType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CallersBaseColumnDto extends BaseDto {
    private String nameInTable;
    private String currentName;
    private VariablesType type;
}
