package com.projects.breakingbook.exposition.exception;

public class EmailAlreadyExistsException extends Exception {
    public EmailAlreadyExistsException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
