package com.example.dialerservice.exceptions;

import com.example.dialerservice.enums.MessageException;

public class DialerServiceException extends RuntimeException {

    private static final long serialVersionUID = -1073076668874757410L;

    public DialerServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DialerServiceException(MessageException messageException, String... args) {
        super(String.format(messageException.getMessage(), args));
    }
}
