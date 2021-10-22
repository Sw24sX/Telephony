package com.example.telephony.mapper;

import com.example.telephony.domain.Sound;
import com.example.telephony.dto.SoundDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SoundMapper {
    @Mapping(target = "id", ignore = true)
    Sound soundDtoToSound(SoundDto soundDto);

    SoundDto soundToSoundDto(Sound sound);

    List<SoundDto> listSoundToListSoundDto(List<Sound> sounds);
}
