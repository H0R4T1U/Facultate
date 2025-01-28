package org.example.lab6.domain.validators;

import org.example.lab6.domain.User;

import java.util.function.Predicate;

public class UserValidator implements Validator<User> {

    Predicate<String> containsDigits = x -> x.matches(".*[0-9]+.*");

    @Override
    public void validate(User entity) throws ValidationException {
        if (entity.getLastName().isEmpty() || entity.getFirstName().isEmpty() || entity.getUsername().isEmpty())
            throw new ValidationException("Name must not be null!\n");
        if (containsDigits.test(entity.getLastName()) || containsDigits.test(entity.getFirstName()))
            throw new ValidationException("Name must not contain digits!\n");
    }
}