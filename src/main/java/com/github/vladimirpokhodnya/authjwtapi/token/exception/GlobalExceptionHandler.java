package com.github.vladimirpokhodnya.authjwtapi.token.exception;

import com.github.vladimirpokhodnya.authjwtapi.token.controller.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)

    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {

        String message = "Пользователь с таким именем или почтой уже существует.";

        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(message));

    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }


    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentials(InvalidCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ex.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка сервера: " + ex.getMessage());

    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingParams(MissingServletRequestParameterException ex) {

        String message = ex.getParameterName() + " parameter is missing";

        return ResponseEntity.badRequest().body(message);

    }


}

