package scs.map.Domain.validators;


import scs.map.Domain.User;
public class UserValidator implements Validator<User> {
    @Override
    public void validate(User entity) throws ValidationException {
        if(entity.getUsername().isEmpty() || entity.getPassword().isEmpty() ||
            entity.getPhoneNumber().isEmpty() || entity.getAge() < 15)
            throw new ValidationException("User is not valid");
    }
}
