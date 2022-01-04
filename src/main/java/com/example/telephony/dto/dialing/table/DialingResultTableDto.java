package com.example.telephony.dto.dialing.table;

import com.example.telephony.dto.CallersBaseHeaderDto;
import lombok.Data;

import java.util.List;

@Data
public class DialingResultTableDto {
    private CallersBaseHeaderDto callersBase;
    List<DialingResultTableDto> rows;
}
