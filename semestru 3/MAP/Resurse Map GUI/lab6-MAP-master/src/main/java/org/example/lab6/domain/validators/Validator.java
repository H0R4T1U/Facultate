package org.example.lab6.domain.validators;

public interface Validator<T> {

    void validate(T entity) throws ValidationException;
}
