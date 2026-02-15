package de.rechnungflow.model;

import java.math.BigDecimal;

public class CleaningObject {
    private int id;
    private int clientId;
    private String name;
    private String address;
    private BigDecimal hourlyRate;

    public CleaningObject(int id, int clientId, String name, String address, BigDecimal hourlyRate){
        this.id = id;
        this.clientId = clientId;
        this.name = name;
        this.address = address;
        this.hourlyRate = hourlyRate;
    }


    public int getCleaningObjectId(){
        return id;
    }

    public int getClientId(){
        return clientId;
    }

    public String getName(){
        return name;
    }

    public BigDecimal getHourlyRate(){
        return hourlyRate;
    }
}

