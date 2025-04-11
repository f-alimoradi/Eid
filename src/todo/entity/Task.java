package todo.entity;

import db.Entity;
import db.Trackable;

import java.util.Date;

public class Task extends Entity implements Trackable {
    public enum Status {
        NotStarted, InProgress, Completed;
    }

    public static final int TASK_ENTITY_CODE = 13;

    public String title;
    public String description;
    public Date duoDate;
    public Status status;
    private Date CreationDate;
    private Date LastModificationDate;

    @Override
    public Entity copy() {
        Task copyTask = new Task();
        copyTask.id = id;
        copyTask.title = title;
        copyTask.description = description;
        copyTask.duoDate = duoDate;
        copyTask.status = status;
        Date copyCreationDate = new Date(CreationDate.getTime());
        Date copyLastModificationDate = new Date(LastModificationDate.getTime());
        copyTask.CreationDate = copyCreationDate;
        copyTask.LastModificationDate = copyLastModificationDate;

        return copyTask;
    }
    @Override
    public int getEntityCode() {
        return TASK_ENTITY_CODE;
    }

    @Override
    public void setCreationDate(Date date) {
        CreationDate = date;
    }
    @Override
    public Date getCreationDate() {
        return CreationDate;
    }
    @Override
    public void setLastModificationDate(Date date) {
        LastModificationDate = date;
    }
    public Date getLastModificationDate() {
        return CreationDate;
    }
}
