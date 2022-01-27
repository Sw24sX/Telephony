package com.example.text.to.speech.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Response with created file
 */
@Data
public class CreatedAudioFileResponse {
    /**
     * Id in data base
     */
    private Long id;

    /**
     * Uri to file
     */
    private String uri;

    /**
     * Absolute path to file
     */
    private String path;

    /**
     * Text for synthesis file
     */
    private String text;
}
