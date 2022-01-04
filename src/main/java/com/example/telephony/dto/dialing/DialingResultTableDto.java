package com.example.telephony.dto.dialing;

import com.example.telephony.dto.CallersBaseHeaderDto;
import lombok.Data;

import java.util.List;

@Data
public class DialingResultTableDto {
    private CallersBaseHeaderDto callersBase;
    List<DialingResultTableDto> rows;
}
