package ubb.scs.map.avioane.Domain.Validators;

import ubb.scs.map.avioane.Domain.Order;

public class OrderValidator implements Validator<Order> {
    @Override
    public void validate(Order entity) throws ValidationException {
        if(entity.getDate() == null || entity.getStatus() == null) {
            throw new ValidationException("Invalid order");
        }
    }
}
