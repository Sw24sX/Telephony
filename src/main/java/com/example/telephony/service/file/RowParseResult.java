package com.example.telephony.service.file;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class RowParseResult {
    private final Map<String, String> variables;
    private final boolean isValid;
}
