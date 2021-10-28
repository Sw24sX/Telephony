package com.example.telephony.dto;

import lombok.Data;

import java.util.List;

@Data
public class ScenarioDto {
    private Long id;
    private String name;
    private List<SoundDto> sounds;
}
