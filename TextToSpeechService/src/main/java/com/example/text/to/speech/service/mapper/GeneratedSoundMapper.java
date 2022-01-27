package com.example.text.to.speech.service.mapper;

import com.example.text.to.speech.service.domain.GeneratedSound;
import com.example.text.to.speech.service.dto.CreatedAudioFileResponse;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper between GeneratedSound and CreatedAudioFileResponse
 */
@Mapper(componentModel = "spring")
public interface GeneratedSoundMapper {
    CreatedAudioFileResponse fromGeneratedSound(GeneratedSound sound);

    List<CreatedAudioFileResponse> fromGeneratedSound(List<GeneratedSound> sound);
}
