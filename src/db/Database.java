package db;

import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;

import java.util.ArrayList;
import java.util.HashMap;

public class Database {
    private static ArrayList<Entity> entities = new ArrayList<>();
    private static int entityCount = 0;
    private static HashMap<Integer, Validator> validators = new HashMap<>();

    public static void add(Entity entity) throws InvalidEntityException{
        entityCount++;
        Validator validator = validators.get(entity.getEntityCode());
        validator.validate(entity);
        entity.id = entityCount;
        entities.add(entity.copy());

    }
    public static Entity get(int id) throws EntityNotFoundException {
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).id == id) {
                return entities.get(i).copy();
            }
        }
        throw new EntityNotFoundException(id);
    }
    public static void delete(int id) throws EntityNotFoundException {
        boolean notExist = true;
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).id == id) {
                entities.remove(i);
                notExist = false;
            }
        }
        if (notExist) {
            throw new EntityNotFoundException(id);
        }
    }
    public static void update(Entity entity) throws EntityNotFoundException, InvalidEntityException{
        if (entities.contains(entity)) {
            Validator validator = validators.get(entity.getEntityCode());
            validator.validate(entity);
            entities.add(entities.indexOf(entity), entity.copy());
        }
        else {
            throw new EntityNotFoundException();
        }
    }
    public static void registerValidator(int entityCode, Validator validator) {
        if (validators.containsKey(entityCode)) {
            throw new IllegalArgumentException("Entity code " + entityCode + " already exists");
        }
        validators.put(entityCode, validator);
    }
}
