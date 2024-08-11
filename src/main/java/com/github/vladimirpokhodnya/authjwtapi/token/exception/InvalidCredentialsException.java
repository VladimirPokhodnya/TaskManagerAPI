package com.github.vladimirpokhodnya.authjwtapi.token.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
