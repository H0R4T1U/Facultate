package ubb.scs.map.clinica.Domain.Validators;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}