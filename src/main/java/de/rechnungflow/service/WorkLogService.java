package de.rechnungflow.service;

import com.fasterxml.jackson.core.type.TypeReference;
import de.rechnungflow.model.WorkLog;
import de.rechnungflow.persistance.JsonStorage;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WorkLogService {

    private static final Path FILE_PATH = Path.of("data", "worklogs.json");
    private final JsonStorage storage = new JsonStorage();

    private int nextId = 1;
    private final List<WorkLog> workLogs = new ArrayList<>();

    public void loadFromFile(){
        List <WorkLog> loaded = storage.readList(FILE_PATH, new TypeReference<List<WorkLog>>() {});
        workLogs.clear();
        workLogs.addAll(loaded);

        nextId = workLogs.stream()
                .mapToInt(WorkLog::getId)
                .max()
                .orElse(0)+1;

    }

    public void saveToFile(){ storage.writeList(FILE_PATH, workLogs);}

    public WorkLog createWorkLog(int employeeId, int objectId, LocalDate date, BigDecimal hours){
        WorkLog wl = new WorkLog(nextId++, employeeId, objectId, date, hours);
        workLogs.add(wl);
        saveToFile();
        return wl;
    }

    public List<WorkLog> findByObjectAndPeriod(int objectId, LocalDate from, LocalDate to){
        return workLogs.stream()
                .filter(w -> w.getId() == objectId)
                .filter(w -> !w.getDate().isBefore(from) && !w.getDate().isAfter(to))
                .toList();
    }

    public List<WorkLog> getAll(){
        return new ArrayList<>(workLogs);
    }
}
