package todo.service;

import db.Database;
import db.Entity;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;

import java.util.ArrayList;

import static db.Database.*;

public class StepService {
    public static void saveStep(int taskRef, String title) {
        Step step = new Step();
        step.title = title;
        step.taskRef = taskRef;
        step.status = Step.Status.NotStarted;

        Database.add(step);
    }
    public static void addStep(int taskRef, String title) {
        Step step = new Step();
        step.taskRef = taskRef;
        step.title = title;
        step.status = Step.Status.NotStarted;
        try {
            Database.add(step);
            System.out.println("Step saved successfully.");
            System.out.println("ID: " + step.id);

        } catch (InvalidEntityException e) {
            System.out.println("Cannot save step.");
            System.out.println(e.getMessage() + " ID : " + taskRef);
        }
    }
    public static void deleteByTaskRef(int taskRef) {
        if (get(taskRef) instanceof Task) {
            for (int i = 0; i < getEntitySize(); i++) {
                Entity entity = getEntityByIndex(i);
                if (entity instanceof Step) {
                    delete(entity.id);
                }
            }
        }
    }
    public static void updateStep(int id, String field, String value) {
        Step step = new Step();
        if (get(id) instanceof Step) {
            step = (Step) get(id);
            switch (field) {
                case "title":
                    step.title = value;
                    String old = ((Step) get(id)).title;
                    try {
                        update(step);
                    } catch (EntityNotFoundException e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                    System.out.println("Successfully updated the step.");
                    System.out.println("Field: " + field);
                    System.out.println("Old value: " + old);
                    System.out.println("New value: " + step.title);
                    break;
                case "statue":
                    if (value.equals("not started")){
                        step.status = Step.Status.NotStarted;
                        try {
                            update(step);
                        } catch (EntityNotFoundException e) {
                            System.out.println(e.getMessage());
                            break;
                        }
                    } else if (value.equals("completed")) {
                        step.status = Step.Status.Completed;
                        try {
                            update(step);
                        } catch (EntityNotFoundException e) {
                            System.out.println(e.getMessage());
                            break;
                        }
                    }
                    int stepCounter = 0;
                    Task task = new Task();
                    task = (Task) get(step.taskRef);
                    ArrayList<Step> steps = new ArrayList<>();
                    for (int i = 0; i < getEntitySize(); i++) {
                        Entity entity = getEntityByIndex(i);
                        if (((Step) entity).taskRef == id) {
                            steps.add((Step) entity);
                        }
                    }
                    for (int i = 0; i < steps.size(); i++){
                        if (steps.get(i).status == Step.Status.Completed) {
                            stepCounter++;
                        }
                    }
                    if (stepCounter == steps.size() - 1) {
                        task.status = Task.Status.Completed;
                        try {
                            update(step);
                        } catch (EntityNotFoundException e) {
                            System.out.println(e.getMessage());
                            break;
                        }
                    }
                    System.out.println("Successfully updated the step.");
                    System.out.println("Field: " + field);
                    System.out.println("New value: " + step.status);
                    break;
            }
        }
    }
}
