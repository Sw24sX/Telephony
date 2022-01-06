package com.example.telephony.dto.dialing.table;

import com.example.telephony.enums.VariablesType;
import lombok.Data;

import java.util.Date;

@Data
public class ExtraCallerBaseColumnDto {
    private String id;
    private Date created;
    private String nameInTable;
    private String currentName;
    private VariablesType type;
}
