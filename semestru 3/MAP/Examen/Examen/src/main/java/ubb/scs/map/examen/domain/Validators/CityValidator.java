package ubb.scs.map.examen.domain.Validators;

import ubb.scs.map.examen.domain.City;

public class CityValidator implements Validator<City>{
    @Override
    public void validate(City entity) throws ValidationException {
        if(entity == null  || entity.getName() == null || entity.getName().trim().equals("") || entity.getId() >= 0) {
            throw new ValidationException("City is invalid");
        }
    }
}
