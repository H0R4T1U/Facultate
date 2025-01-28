package ubb.scs.map.clinica.Domain.Validators;

import ubb.scs.map.clinica.Domain.Consultatie;

public class ConsultatieValidator implements Validator<Consultatie> {
    @Override
    public void validate(Consultatie entity) throws ValidationException {
        if(entity == null || entity.getCnp() == null || entity.getData() == null
        || entity.getNume() == null || entity.getIdMedic() == null) {
            throw new ValidationException("Consultatie Invalida");
        }
    }
}
