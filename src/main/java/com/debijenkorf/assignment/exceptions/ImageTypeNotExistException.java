package com.debijenkorf.assignment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ImageTypeNotExistException extends RuntimeException {
    public ImageTypeNotExistException() {
        super();
    }
    public ImageTypeNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
    public ImageTypeNotExistException(String message) {
        super(message);
    }
    public ImageTypeNotExistException(Throwable cause) {
        super(cause);
    }
}
