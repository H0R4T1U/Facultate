package ubb.scs.map.examen.domain.Validators;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}