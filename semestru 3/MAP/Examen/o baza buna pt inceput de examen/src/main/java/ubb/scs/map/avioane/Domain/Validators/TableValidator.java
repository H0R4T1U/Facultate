package ubb.scs.map.avioane.Domain.Validators;

import ubb.scs.map.avioane.Domain.Table;

import java.util.Objects;

public class TableValidator implements Validator<Table> {
    @Override
    public void validate(Table entity) throws ValidationException {
        if(Objects.isNull(entity)) {
            throw new ValidationException("table is invalid");
        }
    }
}
