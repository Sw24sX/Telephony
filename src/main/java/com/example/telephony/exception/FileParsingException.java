package com.example.telephony.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileParsingException extends TelephonyException {
    private static final long serialVersionUID = -7715998975818698660L;

    public FileParsingException(String message) {
        super(message);
    }

    public FileParsingException(Throwable cause) {
        super(cause);
    }
}
