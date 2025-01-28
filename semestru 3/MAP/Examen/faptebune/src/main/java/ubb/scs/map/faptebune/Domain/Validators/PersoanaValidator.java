package ubb.scs.map.faptebune.Domain.Validators;


import ubb.scs.map.faptebune.Domain.Persoana;

public class PersoanaValidator implements Validator<Persoana> {
    @Override
    public void validate(Persoana entity) throws ValidationException {
        if(
                entity.getParola() == null || entity.getParola().isEmpty() ||
                entity.getNume() == null || entity.getNume().isEmpty() ||
                entity.getPrenume().isEmpty() || entity.getOras().isEmpty() ||
                entity.getNumarStrada().isEmpty() || entity.getStrada().isEmpty() ||
                entity.getTelefon().isEmpty()
        )
            throw new ValidationException("User is not valid");
    }
}
