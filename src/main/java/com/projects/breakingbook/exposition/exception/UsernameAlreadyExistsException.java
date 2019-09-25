package com.projects.breakingbook.exposition.exception;

public class UsernameAlreadyExistsException extends Exception {

    public UsernameAlreadyExistsException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
