package com.example.text.to.speech.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Response with created file
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CreatedAudioFileResponse extends SpeechTextRequest {
    /**
     * File name
     */
    private String name;

    /**
     * Uri to file
     */
    private String uri;

    /**
     * Absolute path to file
     */
    private String absolutePath;

    public CreatedAudioFileResponse(SpeechTextRequest request) {
        this.setText(request.getText());
        this.setVoice(request.getVoice());
    }
}
