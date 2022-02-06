package com.example.telephony.mapper;

import com.example.telephony.tts.persistance.domain.GeneratedSound;
import com.example.telephony.dto.GeneratedSoundDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GeneratedSoundMapper {
    GeneratedSound generatedSoundDtoToGeneratedSound(GeneratedSoundDto generatedSoundDto);
    GeneratedSoundDto generatedSoundDtoToGeneratedSound(GeneratedSound generatedSound);
    List<GeneratedSoundDto> listGeneratedSoundToListGeneratedSoundDto(List<GeneratedSound> generatedSounds);
}
