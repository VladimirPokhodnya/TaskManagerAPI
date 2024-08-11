package com.github.vladimirpokhodnya.authjwtapi.token.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
