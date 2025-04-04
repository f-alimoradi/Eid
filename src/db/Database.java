package db;

import db.exception.EntityNotFoundException;
import java.util.ArrayList;

public class Database {
    private static ArrayList<Entity> entities = new ArrayList<>();
    private static int entityCount = 0;

    public static void add(Entity e) {
        entityCount++;
        e.id = entityCount;
        entities.add(e.copy());
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
    public static void update(Entity e) throws EntityNotFoundException {
        if (entities.contains(e)) {
            entities.add(entities.indexOf(e),e.copy());
        }
        else {
            throw new EntityNotFoundException();
        }
    }
}

