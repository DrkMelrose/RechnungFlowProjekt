package de.rechnungflow.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class WorkLog {
    private int id;
    private int employeeId;
    private int objectId;
    private LocalDate date;
    private BigDecimal hours;

    public WorkLog(int id, int employeeId, int objectId, LocalDate date, BigDecimal hours){
        this.id = id;
        this.employeeId = employeeId;
        this.objectId = objectId;
        this.date = date;
        this.hours = hours;
    }

    public int getId() { return id; }
    public LocalDate getDate() { return date; }
    public BigDecimal getHours() { return hours; }
    public int getEmployeeId() { return employeeId; }
    public int getObjectId() { return objectId; }
}
