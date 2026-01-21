package de.rechnungflow.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class InvoiceItem {
    private final String description;
    private final int quantity;
    private final BigDecimal price;

    @JsonCreator
    public InvoiceItem(
            @JsonProperty("description") String description,
            @JsonProperty("quantity") int quantity,
            @JsonProperty("price") BigDecimal price){
        if (description == null || description.isBlank()){
            throw new IllegalArgumentException("Description must not be blank");
        }
        if (quantity <= 0){
            throw new IllegalArgumentException("The number must be positiv");
        }

        if (price == null){
            throw new IllegalArgumentException("The price must not be null");
        }

        if (price.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("The price must be positive");
        }

        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    @JsonIgnore
    public BigDecimal getTotal(){
        return price.multiply(BigDecimal.valueOf(quantity)) ;
    }

    public String getDescription(){
        return description;
    }

    public int getQuantity(){
        return quantity;
    }

    public BigDecimal getPrice(){
        return price;
    }
}
