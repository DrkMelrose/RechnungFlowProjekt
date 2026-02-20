package de.rechnungflow.service;

import de.rechnungflow.model.WorkLog;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WorkLogService {
    private int nextId = 1;
    private final List<WorkLog> workLogs = new ArrayList<>();

    public WorkLog createWorkLog(int employeeId, int objectId, LocalDate date, BigDecimal hours){
        WorkLog wl = new WorkLog(nextId++, employeeId, objectId, date, hours);
        workLogs.add(wl);
        return wl;
    }

    public List<WorkLog> findByObjectAndPeriod(int objectId, LocalDate from, LocalDate to){
        return workLogs.stream()
                .filter(w -> w.getId() == objectId)
                .filter(w -> !w.getDate().isBefore(from) && !w.getDate().isAfter(to))
                .toList();
    }
}
