package com.example.telephony.dto.caller.base;

import com.example.telephony.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CallersBaseHeaderDto extends BaseDto {
    private boolean isConfirmed;
    private int countCallers;
    private int countInvalidCallers;
    private List<CallersBaseColumnDto> columns;
    private String name;
    private Date updated;
}
