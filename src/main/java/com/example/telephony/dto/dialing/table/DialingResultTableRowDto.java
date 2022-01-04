package com.example.telephony.dto.dialing.table;

import com.example.telephony.dto.CallerDto;
import lombok.Data;

import java.util.List;

@Data
public class DialingResultTableRowDto {
    private List<String> answers;
    private CallerDto caller;
}
