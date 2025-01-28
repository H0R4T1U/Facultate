package ubb.scs.map.avioane.Domain.Validators;

import ubb.scs.map.avioane.Domain.Tables;

import java.util.Objects;

public class TableValidator implements Validator<Tables> {
    @Override
    public void validate(Tables entity) throws ValidationException {
        if(Objects.isNull(entity)) {
            throw new ValidationException("table is invalid");
        }
    }
}
