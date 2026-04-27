package com.rechnung_flow.RechnungFlowService.model.enteties;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class InvoiceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private BigDecimal hours;
    private BigDecimal price;

    @ManyToOne
    private Invoice invoice;

    public InvoiceItem(){

    }

    public InvoiceItem(String description, BigDecimal hours, BigDecimal price){
        this.description = description;
        this.hours = hours;
        this.price = price;
    }

    public void setInvoice(Invoice invoice){
        this.invoice = invoice;
    }

    public BigDecimal getTotal(){return price.multiply(hours);}
    public String getDescription(){return description;}
    public BigDecimal getHours(){return hours;}
    public BigDecimal getPrice(){return price;}


}
