package ubb.scs.map.Domain.validators;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}