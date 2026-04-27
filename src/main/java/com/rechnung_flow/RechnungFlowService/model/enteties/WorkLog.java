package com.rechnung_flow.RechnungFlowService.model.enteties;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Locale;

@Entity
public class WorkLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "object_id")
    private CleaningObject cleaningObject;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    LocalDate date;
    BigDecimal hours;
    String description;
    boolean approved;

    public WorkLog(){

    }

    public WorkLog(Employee employee, CleaningObject cleaningObject, LocalDate date, BigDecimal hours, String description, boolean approved){
        this.date = date;
        this.hours = hours;
        this.description = description;
        this.approved = approved;
    }

    public Long getId(){
        return id;
    }

    public Invoice getInvoice(){
        return invoice;
    }

    public Employee getEmployee(){
        return employee;
    }

    public CleaningObject getObject(){
        return cleaningObject;
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

    public void setInvoice(Invoice invoice){
        this.invoice = invoice;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setEmployee(Employee employee){
        this.employee = employee;
    }

    public void setObject(CleaningObject cleaningObject){
        this.cleaningObject = cleaningObject;
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
