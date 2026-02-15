package de.rechnungflow.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CleaningObjectService {

    private int nextId = 1;
    private final List<CleaningObject> cleaningObjects = new ArrayList<>();

    public CleaningObject createCleaningObject(int clientId, String name, String address, BigDecimal hourlyRate){
        CleaningObject cleaningObject = new CleaningObject(nextId++, clientId, name, address, hourlyRate);
        cleaningObjects.add(cleaningObject);
        return cleaningObject;
    }

    public CleaningObject findCleaningObjectById(int id){
        return cleaningObjects.stream()
                .filter(o -> o.getCleaningObjectId() == id)
                .findFirst()
                .orElse(null);
    }
}
