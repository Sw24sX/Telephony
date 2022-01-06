package com.example.telephony.dto.dialing.table;

import lombok.Data;

import java.util.Date;

@Data
public class ExtraCallerVariablesDto {
    private String id;
    private Date created;
    private String value;
    private boolean isValid;
    private boolean isPhoneColumn;
}
