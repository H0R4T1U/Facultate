package org.example.lab6.domain.validators;

import org.example.lab6.domain.Friendship;
import org.example.lab6.domain.Entity;

import java.util.Objects;
import java.util.function.Predicate;

public class FriendshipValidator implements Validator<Friendship> {

    Predicate<Entity<Long>> isIDNull = Objects::isNull;
    Predicate<Long> isEntityNull = Objects::isNull;

    @Override
    public void validate(Friendship entity) throws ValidationException {
        if (isIDNull.test(entity.getId()) || isEntityNull.test(entity.getId().getLeft())  || isEntityNull.test(entity.getId().getRight()))
            throw new ValidationException("Friendship ID-s cannot be null!\n");
        if (entity.getId().getLeft().toString().isEmpty() || entity.getId().getRight().toString().isEmpty())
            throw new ValidationException("ID-s don't exist!\n");
    }
}
