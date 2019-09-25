package com.projects.breakingbook.exception;

public class EmailAlreadyExistsException extends Exception {
    public EmailAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
