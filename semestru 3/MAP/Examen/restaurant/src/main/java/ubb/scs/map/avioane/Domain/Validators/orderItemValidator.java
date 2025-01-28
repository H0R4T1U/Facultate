package ubb.scs.map.avioane.Domain.Validators;

import ubb.scs.map.avioane.Domain.OrderItem;

public class orderItemValidator implements Validator<OrderItem> {
    @Override
    public void validate(OrderItem entity) throws ValidationException {
        if(entity == null) throw new ValidationException("orderItem is null");
    }
}
