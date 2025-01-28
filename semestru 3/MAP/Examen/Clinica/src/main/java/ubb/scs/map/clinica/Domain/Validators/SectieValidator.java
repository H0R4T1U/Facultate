package ubb.scs.map.clinica.Domain.Validators;

import ubb.scs.map.clinica.Domain.Sectie;

public class SectieValidator implements Validator<Sectie> {
    @Override
    public void validate(Sectie entity) throws ValidationException {
        if(entity == null || entity.getNume() == null ) {
            throw new ValidationException("Sectia este invalida!");
        }
    }
}
