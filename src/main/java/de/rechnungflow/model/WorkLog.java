package de.rechnungflow.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

public class WorkLog {
    private int id;
    private int employeeId;
    private int objectId;
    private LocalDate date;
    private BigDecimal hours;
    private String description;
    private boolean approved;

    @JsonCreator
    public WorkLog(
            @JsonProperty("id")int id,
            @JsonProperty("employeeId")int employeeId,
            @JsonProperty("objectId")int objectId,
            @JsonProperty("date")LocalDate date,
            @JsonProperty("hours")BigDecimal hours,
            @JsonProperty("description")String description,
            @JsonProperty("approved")boolean approved
    ){
        this.id = id;
        this.employeeId = employeeId;
        this.objectId = objectId;
        this.date = date;
        this.hours = hours;
        this.description = description;
        this.approved = approved;
    }

    public int getId() { return id; }
    public LocalDate getDate() { return date; }
    public BigDecimal getHours() { return hours; }
    public int getEmployeeId() { return employeeId; }
    public int getObjectId() { return objectId; }
    public String getDescription(){return description; }
    public void setDescription(){this.description = description;}
    public boolean getApprove(){return approved; }
    public void setApprove(boolean approve){this.approved = approve;}
}
