package todo.validator;

import db.Entity;
import db.Validator;
import db.exception.InvalidEntityException;
import todo.entity.Task;

public class TaskValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException, IllegalArgumentException {
        if (!(entity instanceof Task)) {
            throw new IllegalArgumentException("Entity should be Task!");
        }
        if (((Task) entity).title == null) {
            throw new InvalidEntityException("Task's title shouldn't be empty!");
        }
    }
}
