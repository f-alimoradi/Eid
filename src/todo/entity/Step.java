package todo.entity;

import db.Entity;

public class Step extends Entity {
    public enum Status {
        NotStarted, Completed;
    }

    public static final int STEP_ENTITY_CODE = 19;

    public String title;
    public Status status;
    public int taskRef;

    @Override
    public Entity copy() {
        Step copyStep = new Step();
        copyStep.id = id;
        copyStep.title = title;
        copyStep.status = status;
        copyStep.taskRef = taskRef;

        return copyStep;
    }
    @Override
    public int getEntityCode() {
        return STEP_ENTITY_CODE;
    }
}
