package com.projects.breakingbook.exception;

public class UsernameAlreadyExistsException extends Exception {

    public UsernameAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
