package com.example.telephony.tts.exception;

import com.example.telephony.exception.TelephonyException;
import com.example.telephony.tts.persistance.enums.TTSExceptionMessage;

public class TextToSpeechException extends TelephonyException {

    public TextToSpeechException(String message, Throwable cause) {
        super(cause, message);
    }

    public TextToSpeechException(TTSExceptionMessage message, Throwable cause, String... args) {
        super(cause, String.format(message.getMessage(), args));
    }

    public TextToSpeechException(TTSExceptionMessage message, String... args) {
        super(String.format(message.getMessage(), args));
    }

    public TextToSpeechException(String message) {
        super(message);
    }
}
