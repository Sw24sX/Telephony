package com.example.telephony.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GeneratedSoundDto extends BaseDto {
    private String path;
    private String uri;
}
