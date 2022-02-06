package com.example.telephony.tts.mapper;

import com.example.telephony.tts.persistance.domain.GeneratedSound;
import com.example.telephony.tts.dto.GeneratedSoundDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GeneratedSoundMapper {
    GeneratedSoundDto generatedSoundDtoToGeneratedSound(GeneratedSound generatedSound);
    List<GeneratedSoundDto> listGeneratedSoundToListGeneratedSoundDto(List<GeneratedSound> generatedSounds);
}
