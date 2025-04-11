import db.Database;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Task;
import todo.service.StepService;
import todo.service.TaskService;

import java.util.Scanner;
import java.util.Date;

import static db.Database.delete;
import static db.Database.get;


public static void main(String[] args) throws InvalidEntityException {
    Scanner scanner = new Scanner(System.in);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    while (true) {
        String command = scanner.nextLine().trim();
        switch (command) {
            case "add task":
                System.out.println("title: ");
                String taskTitle = scanner.nextLine();
                System.out.println("description: ");
                String description = scanner.nextLine();
                System.out.println("Dou Date: ");
                String input = scanner.nextLine();
                try {
                    Date duoDate = simpleDateFormat.parse(input);
                    TaskService.addTask(taskTitle, description, duoDate);
                }catch (ParseException e) {
                    System.out.println("Cannot save task.");
                    System.out.println("Error: Invalid date format.");
                }
                break;
            case "add step":
                System.out.println("TaskID: ");
                int taskID = scanner.nextInt();
                System.out.println("description: ");
                String stepTitle = scanner.nextLine();
                StepService.addStep(taskID, stepTitle);
                break;
            case "delete":
                int ID = scanner.nextInt();
                try {
                    if (!(get(ID) instanceof Task)) {
                        TaskService.deleteTask(ID);
                    } else {
                        try {
                            delete(ID);
                            System.out.println("Entity with ID=" + ID + "successfully deleted.");
                        }catch (EntityNotFoundException e) {
                            System.out.println("Cannot delete entity with ID=" + ID + ".");
                            System.out.println("Error: Something happend");
                        }
                    }
                } catch (EntityNotFoundException e) {
                    System.out.println("Cannot delete entity with ID=" + ID + ".");
                    System.out.println("Error: Something happend");
                }
                break;
            case "update task":
                System.out.println("ID: ");
                int id = scanner.nextInt();
                System.out.println("Field: ");
                String field = scanner.nextLine();
                System.out.println("New Value: ");
                String value = scanner.nextLine();
                TaskService.updateTask(id, field, value);
                break;
            case "update step":
                System.out.println("ID: ");
                int Id = scanner.nextInt();
                System.out.println("Field: ");
                String Field = scanner.nextLine();
                System.out.println("New Value: ");
                String Value = scanner.nextLine();
                StepService.updateStep(Id, Field,Value);
                break;
            case "get task-by-id":
                System.out.println("ID: ");
                int idd = scanner.nextInt();
                TaskService.getTaskById(idd);
                break;
            case "get all-tasks":
                TaskService.getAllTasks();
                break;
            case "get incomplete-tasks":
                TaskService.getIncompleteTasks();
                break;
            case "exit":
                System.out.println("Exit <<<<<");
                return;
            default:
                System.out.println("Invalid Command.");
        }
    }
}