package com.example.text.to.speech.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreatedAudioFileResponse extends SpeechTextRequest {
    private String name;
    private String uri;
    private String absolutePath;

    public CreatedAudioFileResponse(SpeechTextRequest request) {
        this.setText(request.getText());
        this.setVoice(request.getVoice());
    }
}
