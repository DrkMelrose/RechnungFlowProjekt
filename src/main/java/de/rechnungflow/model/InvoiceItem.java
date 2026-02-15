package de.rechnungflow.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class InvoiceItem {
    private final String description;
    private final BigDecimal hours;
    private final BigDecimal price;

    @JsonCreator
    public InvoiceItem(
            @JsonProperty("description") String description,
            @JsonProperty("hours") BigDecimal hours,
            @JsonProperty("price") BigDecimal price){
        if (description == null || description.isBlank()){
            throw new IllegalArgumentException("Description must not be blank");
        }
        if (hours.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("The number must be positiv");
        }

        if (price == null){
            throw new IllegalArgumentException("The price must not be null");
        }

        if (price.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("The price must be positive");
        }

        this.description = description;
        this.hours = hours;
        this.price = price;
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public BigDecimal getTotal(){
        return price.multiply(hours) ;
    }

    public String getDescription(){
        return description;
    }

    public BigDecimal getHours(){
        return hours;
    }

    public BigDecimal getPrice(){
        return price;
    }
}
