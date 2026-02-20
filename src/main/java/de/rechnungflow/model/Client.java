package de.rechnungflow.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Client {
    int id;
    private String companyName;
    private String contactPerson;
    private String email;
    private String phone;

    @JsonCreator
    public Client(
            @JsonProperty("id")int id,
            @JsonProperty("companyName")String companyName,
            @JsonProperty("contactPerson")String contactPerson,
            @JsonProperty("email")String email,
            @JsonProperty("phone")String phone
    ){
        this.id = id;
        this.companyName = companyName;
        this.contactPerson = contactPerson;
        this.email = email;
        this.phone = phone;
    }

    public Client(String companyName, String contactPerson, String email, String phone){
        this.companyName = companyName;
        this.contactPerson = contactPerson;
        this.email = email;
        this.phone = phone;
    }


    public int getId(){
        return id;
    }

    public String getCompanyName() { return companyName; }

    public String getPhone() { return phone; }

    public String getContactPerson(){
        return contactPerson;
    }

    public void setContactPerson(String contactPerson){
        this.contactPerson = contactPerson;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

}
