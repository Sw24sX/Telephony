package com.example.text.to.speech.service.exception;

import com.example.text.to.speech.service.enums.ExceptionMessages;

public class TextToSpeechException extends RuntimeException {

    private static final long serialVersionUID = -794279460290584794L;

    public TextToSpeechException(String message, Throwable cause) {
        super(message, cause);
    }

    public TextToSpeechException(ExceptionMessages message) {
        super(message.getMessage());
    }
}
