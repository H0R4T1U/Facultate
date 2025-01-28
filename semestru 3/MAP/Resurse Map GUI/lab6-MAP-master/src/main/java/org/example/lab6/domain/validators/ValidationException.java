package org.example.lab6.domain.validators;

public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
