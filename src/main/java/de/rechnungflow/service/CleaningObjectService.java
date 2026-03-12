package de.rechnungflow.service;

import com.fasterxml.jackson.core.type.TypeReference;
import de.rechnungflow.model.CleaningObject;
import de.rechnungflow.persistance.JsonStorage;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CleaningObjectService {

    private final static Path FILE_PATH = Path.of("data", "cleaningObject.json");
    private final JsonStorage storage = new JsonStorage();
    private final Path filePath;

    private int nextId = 1;
    private final List<CleaningObject> cleaningObjects = new ArrayList<>();

    public CleaningObjectService(Path filePath){
        this.filePath = filePath;
        loadFromFile();
    }

    public CleaningObjectService(){
        this(Paths.get("cleaningObject.json"));
    }

    public void loadFromFile(){
        List<CleaningObject> loaded = storage.readList(filePath, new TypeReference<List<CleaningObject>>() {});
        cleaningObjects.clear();
        cleaningObjects.addAll(loaded);

        nextId = cleaningObjects.stream()
                .mapToInt(CleaningObject::getCleaningObjectId)
                .max()
                .orElse(0)+1;
    }

    public void saveToFile(){
        storage.writeList(filePath, cleaningObjects);
    }

    public CleaningObject createCleaningObject(int clientId, String name, String address, BigDecimal hourlyRate,
                                               BigDecimal fixedMonthPrice, boolean active){
        CleaningObject cleaningObject = new CleaningObject(nextId++, clientId, name, address, hourlyRate,
                fixedMonthPrice, active);
        cleaningObjects.add(cleaningObject);
        saveToFile();
        return cleaningObject;
    }

    public Optional<CleaningObject> findCleaningObjectById(int id){
        return cleaningObjects.stream()
                .filter(o -> o.getCleaningObjectId() == id)
                .findFirst();
    }

    public List<CleaningObject> getAll(){
        return new ArrayList<>(cleaningObjects);
    }

    public void add(CleaningObject obj){ cleaningObjects.add(obj); }
}
