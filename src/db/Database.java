package db;

import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.text.SimpleDateFormat;

public class Database {
    private static final ArrayList<Entity> entities = new ArrayList<>();
    private static int entityCount = 0;
    private static HashMap<Integer, Validator> validators = new HashMap<>();

    public static void add(Entity entity) throws InvalidEntityException{
        entityCount++;
        if (validators.containsKey(entity.getEntityCode())) {
            Validator validator = validators.get(entity.getEntityCode());
            validator.validate(entity);
        }
        entity.id = entityCount;
        if (entity instanceof Trackable trackable) {
            Date currentDate = new Date();
            trackable.setCreationDate(currentDate);
            trackable.setLastModificationDate(currentDate);
        }
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
        boolean notExist = true;
        int index = 0;
        for (Entity e : entities) {
            if (e.id == entity.id) {
                if (validators.containsKey(entity.getEntityCode())) {
                    Validator validator = validators.get(entity.getEntityCode());
                    validator.validate(entity);
                }
                if (entity instanceof Trackable trackable) {
                    Date currentDate = new Date();
                    trackable.setLastModificationDate(currentDate);
                }
                notExist = false;
                index = entities.indexOf(e);
            }
        }
        if (notExist) {
            throw new EntityNotFoundException();
        }
        else {
            entities.add(index, entity.copy());
        }

    }
    public static void registerValidator(int entityCode, Validator validator) {
        if (validators.containsKey(entityCode)) {
            throw new IllegalArgumentException("Entity code " + entityCode + " already exists");
        }
        validators.put(entityCode, validator);
    }
    public static Entity getEntityByIndex(int index) {
            return entities.get(index).copy();
    }
    public static int getEntitySize() {
        return entities.size();
    }
}
