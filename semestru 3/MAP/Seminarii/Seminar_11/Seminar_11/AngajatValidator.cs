namespace Seminar_11;

public class AngajatValidator : IValidator<Angajat>
{
    public void Validate(Angajat entity)
    {
        if (entity == null)
        {
            throw new ArgumentNullException(nameof(entity));
        }
        
    }
}