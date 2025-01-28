package ubb.scs.map.avioane.Domain.Validators;



import ubb.scs.map.avioane.Domain.MenuItem;

import java.util.Objects;

public class MenuItemValidator implements Validator<MenuItem> {
    @Override
    public void validate(MenuItem item) throws ValidationException {
        if(item == null || item.getItem() == null || item.getCategory() == null ||
            item.getCurrency() == null || item.getPrice() <= 0
        ) {
            throw new ValidationException("User is not valid");
        }
    }
}
