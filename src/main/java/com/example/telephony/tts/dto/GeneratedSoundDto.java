package com.example.telephony.tts.dto;

import com.example.telephony.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GeneratedSoundDto extends BaseDto {
    private String path;
    private String uri;
}
