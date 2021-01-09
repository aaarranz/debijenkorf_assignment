package com.debijenkorf.assignment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class GenericException extends RuntimeException {

    public GenericException() { super(); }
    public GenericException(String message, Throwable cause) {
        super(message, cause);
    }
    public GenericException(String message) {
        super(message);
    }
    public GenericException(Throwable cause) {
        super(cause);
    }
}