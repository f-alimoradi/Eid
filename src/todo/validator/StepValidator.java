package todo.validator;

import db.Database;
import db.Entity;
import db.Validator;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;

public class StepValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Step)) {
            throw new IllegalArgumentException("Entity should be Step!");
        }
        if (((Step) entity).title == null) {
            throw new InvalidEntityException("Step's title shouldn't be empty!");
        }
        try {
            if (!(Database.get(((Step) entity).taskRef) instanceof Task)) {
                throw new InvalidEntityException("Task with this ID doesn't exist!");
            }
        } catch (EntityNotFoundException e) {
            throw new InvalidEntityException("This ID doesn't exist in the Database!");
        }
    }
}
