package example;

import db.Entity;
import db.Validator;
import db.exception.InvalidEntityException;

public class HumanValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException, IllegalArgumentException{
        if (!(entity instanceof Human)) {
            throw new IllegalArgumentException("Entity should be Human!");
        }
        if (((Human) entity).name == null) {
            throw new InvalidEntityException("Human's name shouldn't be empty!");
        }
        if (((Human) entity).age < 0) {
            throw new InvalidEntityException("Human's age shouldn't be negative!");
        }

    }
}

