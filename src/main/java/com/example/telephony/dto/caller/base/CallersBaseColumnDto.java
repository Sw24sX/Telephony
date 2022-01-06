package com.example.telephony.dto.caller.base;

import com.example.telephony.dto.BaseDto;
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
