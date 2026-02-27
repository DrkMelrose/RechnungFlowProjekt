package de.rechnungflow.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class CleaningObject {
    private int id;
    private int clientId;
    private String name;
    private String address;
    private BigDecimal hourlyRate;
    private BigDecimal fixedMonthlyPrice;
    private boolean active;

    @JsonCreator
    public CleaningObject(
            @JsonProperty("id")int id,
            @JsonProperty("clientId")int clientId,
            @JsonProperty("name")String name,
            @JsonProperty("address")String address,
            @JsonProperty("hourlyRate")BigDecimal hourlyRate,
            @JsonProperty("fixedMonthPrice")BigDecimal fixedMonthlyPrice,
            @JsonProperty("active")boolean active
    ){
        this.id = id;
        this.clientId = clientId;
        this.name = name;
        this.address = address;
        this.hourlyRate = hourlyRate;
        this.fixedMonthlyPrice = fixedMonthlyPrice;
        this.active = active;
    }


    public int getCleaningObjectId(){
        return id;
    }

    public void setCleaningObjectId(int id) { this.id = id; }

    public int getClientId(){
        return clientId;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){ this.name = name; }

    public String getAddress(){ return address; }

    public void setAddress(String address) { this.address = address; }

    public BigDecimal getHourlyRate(){
        return hourlyRate;
    }

    public void setHourlyRate(BigDecimal hourlyRate){ this.hourlyRate = hourlyRate; }

    public BigDecimal getFixedMonthPrice() {return fixedMonthlyPrice; }
    public void setFixedMonthlyPrice(BigDecimal fixedMonthlyPrice) {this.fixedMonthlyPrice = fixedMonthlyPrice;}

    public boolean getActive(){return active; }
    public void setActive(boolean active) {this.active = active; }
}

