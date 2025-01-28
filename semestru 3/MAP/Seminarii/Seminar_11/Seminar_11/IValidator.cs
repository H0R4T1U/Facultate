namespace Seminar_11;

public interface IValidator<E>
{
    public void Validate(E entity);
}