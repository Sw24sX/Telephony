package com.example.telephony.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SoundDto extends BaseDto {
    private String name;
    private String uri;
}
