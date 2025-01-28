package ubb.scs.map.clinica.Domain.Validators;

import ubb.scs.map.clinica.Domain.Medic;

public class MedicValidator implements Validator<Medic> {
    @Override
    public void validate(Medic entity) throws ValidationException {
        if(entity == null || entity.getNume() == null){
            throw new ValidationException("Medicul este invalid!");
        }
    }
}
