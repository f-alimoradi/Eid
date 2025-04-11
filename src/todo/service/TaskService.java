package todo.service;

import static db.Database.*;

import db.Database;
import db.Entity;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;
import todo.service.StepService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class TaskService {
    public static SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");

    public static void setAsCompleted(int taskId) {
        Task task = new Task();
        try {
            if (Database.get(taskId) instanceof Task) {
                task = (Task) Database.get(taskId);
                task.status = Task.Status.Completed;
                update(task);
            }
        } catch (EntityNotFoundException e) {
            throw new InvalidEntityException("Task with " + taskId + " ID not found!");
        }
    }
    public static void setAsInProgress(int taskId) throws InvalidEntityException {
        Task task = new Task();
        try {
            if (Database.get(taskId) instanceof Task) {
                task = (Task) Database.get(taskId);
                task.status = Task.Status.InProgress;
                update(task);
            }
        } catch (EntityNotFoundException e) {
            throw new InvalidEntityException("Task with " + taskId + " ID not found!");
        }
    }
    public static void setAsNotStarted(int taskId) throws InvalidEntityException {
        Task task = new Task();
        try {
            if (Database.get(taskId) instanceof Task) {
                task = (Task) Database.get(taskId);
                task.status = Task.Status.NotStarted;
                update(task);
            }
        } catch (EntityNotFoundException e) {
            throw new InvalidEntityException("Task with " + taskId + " ID not found!");
        }
    }
    public static void addTask(String title, String description, Date duoDate) {
        Task task = new Task();
        task.status = Task.Status.Completed;
        task.duoDate = duoDate;
        task.title = title;
        task.description = description;
        try {
            Database.add(task);
            System.out.println("Task saved successfully.");
            System.out.println("ID : " + task.id);
        } catch (InvalidEntityException e) {
            System.out.println("Cannot save task.");
            System.out.println(e.getMessage());
        }
    }
    public static void deleteTask(int id) {
        try {
            delete(id);
            StepService.deleteByTaskRef(id);
            System.out.println("Entity with ID=" + id + "successfully deleted.");
        } catch (EntityNotFoundException e) {
            System.out.println("Cannot delete entity with ID=" + id + ".");
            System.out.println("Error: Something happend");
        }
    }
    public static void updateTask(int id, String field, String value){
        Task task = new Task();
        if (get(id) instanceof Task) {
            task = (Task) get(id);
            switch (field) {
                case "title":
                    task.title = value;
                    String old = ((Task) get(id)).title;
                    update(task);
                    System.out.println("Successfully updated the task.");
                    System.out.println("Field: " + field);
                    System.out.println("Old value: " + old);
                    System.out.println("New value: " + task.title);
                    System.out.println("Modification Date: " + task.getLastModificationDate());
                    break;
                case "description":
                    task.description = value;
                    String Old = ((Task) get(id)).description;
                    update(task);
                    System.out.println("Successfully updated the task.");
                    System.out.println("Field: " + field);
                    System.out.println("Old value: " + Old);
                    System.out.println("New value: " + task.description);
                    System.out.println("Modification Date: " + task.getLastModificationDate());
                    break;
                case "duo date":
                    try {
                        Date duoDate = simpleDate.parse(value);
                        task.duoDate = duoDate;
                        Date oLd = ((Task) get(id)).duoDate;
                        update(task);
                        System.out.println("Successfully updated the task.");
                        System.out.println("Field: " + field);
                        System.out.println("Old value: " + oLd);
                        System.out.println("New value: " + task.duoDate);
                        System.out.println("Modification Date: " + task.getLastModificationDate());
                    } catch (ParseException e) {
                        System.out.println("Cannot update task with ID=" + id + ".");
                        System.out.println("Error: Invalid date format.");
                    }
                    break;
                case "status":
                    if (value.equals("in progress")){
                        setAsInProgress(id);
                    } else if (value.equals("not started")){
                        setAsNotStarted(id);
                    } else if (value.equals("completed")){
                        setAsCompleted(id);
                        for (int i = 0; i < getEntitySize(); i++) {
                            Entity entity = getEntityByIndex(i);
                            if (entity instanceof Step) {
                                if (((Step) entity).taskRef == id) {
                                    ((Step) entity).status = Step.Status.Completed;
                                }
                            }
                        }
                    }
                    update(task);
                    System.out.println("Successfully updated the task.");
                    System.out.println("Field: " + field);
                    System.out.println("New value: " + task.status);
                    System.out.println("Modification Date: " + task.getLastModificationDate());
                    break;
            }

        }
        else {
            System.out.println("Cannot update task with ID=" + id + ".");
            System.out.println("Error: Cannot find entity with id=" + id);
        }

    }
    public static void getTaskById(int id) {
        if (!(get(id) instanceof Task)) {
            System.out.println("Cannot find task with ID=" + id + ".");
        }
        else {
            Task task ;
            try {
                task = (Task) get(id);
                System.out.println("ID: " + task.id);
                System.out.println("title: " + task.title);
                System.out.println("Duo Date:" + task.duoDate);
                System.out.println("Status: " + task.status);
                System.out.println("Steps:");
                for (int i = 0; i < getEntitySize(); i++) {
                    Entity entity = getEntityByIndex(i);
                    if (entity instanceof Step) {
                        if (((Step) entity).taskRef == id) {
                            System.out.println("+ " + ((Step) entity).title + ":");
                            System.out.println("  ID: " + entity.id);
                            System.out.println("  Status: " + ((Step) entity).status);
                        }
                    }
                }
            } catch (EntityNotFoundException e) {
                System.out.println("Cannot find task with ID=" + id + ".");
            }
        }
    }
    public static void getAllTasks() {
        for (int i = 0; i < getEntitySize(); i++) {
            if (getEntityByIndex(i) instanceof Task) {
                getTaskById(getEntityByIndex(i).id);
            }
        }
    }
    public static void getIncompleteTasks() {
        for (int i = 0; i < getEntitySize(); i++) {
            if (getEntityByIndex(i) instanceof Task) {
                if (((Task) getEntityByIndex(i)).status != Task.Status.Completed) {
                    getTaskById(getEntityByIndex(i).id);
                }
            }
        }
    }
}