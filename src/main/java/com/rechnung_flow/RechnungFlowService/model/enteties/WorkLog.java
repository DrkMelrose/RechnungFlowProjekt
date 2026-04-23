package com.rechnung_flow.RechnungFlowService.model.enteties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Locale;

@Entity
public class WorkLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long employeeId;
    Long objectId;
    LocalDate date;
    BigDecimal hours;
    String description;
    boolean approved;

    public WorkLog(){

    }

    public WorkLog(Long id, Long employeeId, Long objectId, LocalDate date, BigDecimal hours, String description, boolean approved){
        this.id = id;
        this.employeeId = employeeId;
        this.objectId = objectId;
        this.date = date;
        this.hours = hours;
        this.description = description;
        this.approved = approved;
    }

    public Long getId(){
        return id;
    }

    public Long getEmployeeId(){
        return employeeId;
    }

    public Long getObjectId(){
        return objectId;
    }

    public LocalDate getDate(){
        return date;
    }

    public BigDecimal getHours(){
        return hours;
    }

    public String getDescription(){
        return description;
    }

    public boolean getApproved(){
        return approved;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setEmployeeId(Long employeeId){
        this.employeeId = employeeId;
    }

    public void setObjectId(Long objectId){
        this.objectId = objectId;
    }

    public void setDate(LocalDate date){
        this.date = date;
    }

    public void setHours(BigDecimal hours){
        this.hours = hours;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setApproved(boolean approved){
        this.approved = approved;
    }

}
