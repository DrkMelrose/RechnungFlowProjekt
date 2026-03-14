package de.rechnungflow.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import de.rechnungflow.model.WorkLog;
import de.rechnungflow.persistance.JsonStorage;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WorkLogService {

    private static final Path FILE_PATH = Path.of("data", "worklogs.json");
    private final JsonStorage storage = new JsonStorage();
    private final Path filePath;
    private int nextId = 1;
    private final List<WorkLog> workLogs = new ArrayList<>();

    public WorkLogService(Path filePath){
        this.filePath = filePath;
        loadFromFile();
    }

    public WorkLogService(){
        this(Paths.get("data/worklogs.json"));
    }

    public WorkLog create(
            int employeeId,
            int objectId,
            LocalDate date,
            BigDecimal hours,
            String description,
            boolean approved){
        if (hours == null || hours.compareTo(BigDecimal.ZERO) <= 0){
            return null;
        }


        WorkLog workLog = new WorkLog(
                nextId++,
                employeeId,
                objectId,
                date,
                hours,
                description,
                approved
        );
        workLogs.add(workLog);
        saveToFile();

        return workLog;
    }

    public void loadFromFile(){
        List <WorkLog> loaded = storage.readList(filePath, new TypeReference<List<WorkLog>>() {});
        workLogs.clear();
        workLogs.addAll(loaded);

        nextId = workLogs.stream()
                .mapToInt(WorkLog::getId)
                .max()
                .orElse(0)+1;

    }

    public void saveToFile(){ storage.writeList(filePath, workLogs);}

    public WorkLog createWorkLog(int employeeId, int objectId, LocalDate date, BigDecimal hours,
                                 String description, boolean approve){
        if (hours == null || hours.compareTo(BigDecimal.ZERO) <= 0){
            return null;
        }
        WorkLog wl = new WorkLog(nextId++, employeeId, objectId, date, hours, description, approve);
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

    public void add(WorkLog workLog){
        workLogs.add(workLog);
    }

    public Optional<WorkLog> findById(int id){
        return workLogs.stream()
                .filter(wl -> wl.getId() == id)
                .findFirst();
    }
}
