package com.rechnung_flow.RechnungFlowService.model.enteties;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String contactPerson;
    private String email;
    private String phone;

    public Client(){

    }

    public Client(String companyName, String contactPerson, String email, String phone){
        this.companyName = companyName;
        this.contactPerson = contactPerson;
        this.email = email;
        this.phone = phone;
    }

    public Long getId(){
        return id;
    }

    public String getCompanyName(){
        return companyName;
    }

    public String getContactPerson(){
        return contactPerson;
    }

    public String getEmail(){
        return email;
    }

    public String getPhone(){
        return phone;
    }

    public void setId(Long Id){
        this.id = id;
    }

    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    public void setContactPerson(String contactPerson){
        this.contactPerson = contactPerson;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }
}
