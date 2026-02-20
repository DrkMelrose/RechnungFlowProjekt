package de.rechnungflow.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee {
    private int id;
    private String name;
    private String phone;
    private String email;

    @JsonCreator
    public Employee(
            @JsonProperty("id")int id,
            @JsonProperty("name")String name,
            @JsonProperty("phone")String phone,
            @JsonProperty("email")String email
    ) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public int getEmployeesId(){
        return id;
    }
    public void setEmployeesId(int id){ this.id = id; }

    public String getNameOfEmployee() { return name; }
    public String getPhoneOfEmployee(){ return phone; }
    public String getEmailOfEmployee() {return email; }

    public void setPhoneOfEmployee(String phone) { this.phone = phone; }
    public void setEmailOfEmployee(String email) { this.email = email; }
    public void setNameOfEmployee(String name) { this.name = name; }
}


