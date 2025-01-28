package ubb.scs.map.Domain.validators;

import ubb.scs.map.Domain.Message;

public class MessageValidator implements Validator<Message> {
    @Override
    public void validate(Message entity) throws ValidationException {
        if("".equals(entity.getText())){
            throw new ValidationException("Text is empty");
        }
    }
}
