package com.debijenkorf.assignment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SourceUrlDownException extends RuntimeException {

    public SourceUrlDownException() { super(); }
    public SourceUrlDownException(String message, Throwable cause) {
        super(message, cause);
    }
    public SourceUrlDownException(String message) {
        super(message);
    }
    public SourceUrlDownException(Throwable cause) {
        super(cause);
    }
}

