package ubb.scs.map.faptebune.Domain.Validators;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}