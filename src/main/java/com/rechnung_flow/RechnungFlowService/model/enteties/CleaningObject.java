package com.rechnung_flow.RechnungFlowService.model.enteties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity
public class CleaningObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clientId;
    private String name;
    private String address;
    private BigDecimal hourlyRate;
    private BigDecimal fixedMonthlyPrice;
    private boolean active;

    public CleaningObject(){

    }

    public CleaningObject(Long id, Long clientId, String name, String address, BigDecimal hourlyRate, BigDecimal fixedMonthlyPrice, boolean active){
        this.id = id;
        this.clientId = clientId;
        this.name = name;
        this.address = address;
        this.hourlyRate = hourlyRate;
        this.fixedMonthlyPrice = fixedMonthlyPrice;
        this.active = active;
    }

    public Long getId(){
        return id;
    }

    public Long getClientId(){
        return clientId;
    }

    public String getName(){
        return name;
    }

    public String getAddress(){
        return address;
    }

    public BigDecimal getHourlyRate(){
        return hourlyRate;
    }

    public BigDecimal getFixedMonthlyPrice(){
        return fixedMonthlyPrice;
    }

    public boolean getActive(){
        return active;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setClientId(Long clientId){
        this.clientId = clientId;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void setHourlyRate(BigDecimal hourlyRate){
        this.hourlyRate = hourlyRate;
    }

    public void setFixedMonthlyPrice(BigDecimal fixedMonthlyPrice){
        this.fixedMonthlyPrice = fixedMonthlyPrice;
    }

    public void setActive(boolean active){
        this.active = active;
    }
}
