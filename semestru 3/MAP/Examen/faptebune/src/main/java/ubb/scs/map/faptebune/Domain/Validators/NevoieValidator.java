package ubb.scs.map.faptebune.Domain.Validators;

import ubb.scs.map.faptebune.Domain.Nevoie;

public class NevoieValidator implements Validator<Nevoie> {
    @Override
    public void validate(Nevoie entity) throws ValidationException {
        if(
                entity.getDescriere().isEmpty() ||
                entity.getDeadline() == null ||
                entity.getOmInNevoie() == null ||
                entity.getStatus().isEmpty() ||
                entity.getTitle().isEmpty() ||
                entity.getOmSalvator() == null
        ){
            throw new ValidationException("Nevoie invalida!");
        }
    }
}
