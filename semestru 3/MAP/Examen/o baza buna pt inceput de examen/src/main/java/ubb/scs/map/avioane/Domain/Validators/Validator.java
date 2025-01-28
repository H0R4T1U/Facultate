package ubb.scs.map.avioane.Domain.Validators;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}