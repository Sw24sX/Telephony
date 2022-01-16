package com.example.telephony.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileParsingException extends TelephonyException {
    public FileParsingException(String message) {
        super(message);
    }
}
