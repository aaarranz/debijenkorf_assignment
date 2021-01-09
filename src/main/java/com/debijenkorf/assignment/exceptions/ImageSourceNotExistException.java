package com.debijenkorf.assignment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ImageSourceNotExistException extends RuntimeException {

    public ImageSourceNotExistException() { super(); }
    public ImageSourceNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
    public ImageSourceNotExistException(String message) {
        super(message);
    }
    public ImageSourceNotExistException(Throwable cause) {
        super(cause);
    }
}

