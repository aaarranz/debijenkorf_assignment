package com.debijenkorf.assignment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UploadToStorageException extends RuntimeException {

    public UploadToStorageException() { super(); }
    public UploadToStorageException(String message, Throwable cause) {
        super(message, cause);
    }
    public UploadToStorageException(String message) {
        super(message);
    }
    public UploadToStorageException(Throwable cause) {
        super(cause);
    }
}

