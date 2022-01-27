package com.example.text.to.speech.service.exception;

import com.example.text.to.speech.service.enums.ExceptionMessages;

/**
 * Base extension for service
 */
public class TextToSpeechException extends RuntimeException {

    private static final long serialVersionUID = -794279460290584794L;

    public TextToSpeechException(String message, Throwable cause) {
        super(message, cause);
    }

    public TextToSpeechException(String message) {
        super(message);
    }

    public TextToSpeechException(ExceptionMessages message, String... args) {
        super(String.format(message.getMessage(), (Object[]) args));
    }
}
