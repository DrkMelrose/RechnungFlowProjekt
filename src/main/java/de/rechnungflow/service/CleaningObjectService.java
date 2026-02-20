package de.rechnungflow.service;

import com.fasterxml.jackson.core.type.TypeReference;
import de.rechnungflow.model.CleaningObject;
import de.rechnungflow.persistance.JsonStorage;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CleaningObjectService {

    private final static Path FILE_PATH = Path.of("data", "cleaningObject.json");
    private final JsonStorage storage = new JsonStorage();

    private int nextId = 1;
    private final List<CleaningObject> cleaningObjects = new ArrayList<>();

    public void loadFromFile(){
        List<CleaningObject> loaded = storage.readList(FILE_PATH, new TypeReference<List<CleaningObject>>() {});
        cleaningObjects.clear();
        cleaningObjects.addAll(loaded);

        nextId = cleaningObjects.stream()
                .mapToInt(CleaningObject::getCleaningObjectId)
                .max()
                .orElse(0)+1;
    }

    public void saveToFile(){
        storage.writeList(FILE_PATH, cleaningObjects);
    }

    public CleaningObject createCleaningObject(int clientId, String name, String address, BigDecimal hourlyRate){
        CleaningObject cleaningObject = new CleaningObject(nextId++, clientId, name, address, hourlyRate);
        cleaningObjects.add(cleaningObject);
        saveToFile();
        return cleaningObject;
    }

    public CleaningObject findCleaningObjectById(int id){
        return cleaningObjects.stream()
                .filter(o -> o.getCleaningObjectId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<CleaningObject> getAll(){
        return new ArrayList<>(cleaningObjects);
    }
}
