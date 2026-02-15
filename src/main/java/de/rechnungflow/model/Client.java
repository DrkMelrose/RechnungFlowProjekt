package de.rechnungflow.model;

public class Client {
    int id;
    private String companyName;
    private String contactPerson;
    private String email;
    private String phone;


    public Client(int id, String companyName, String contactPerson, String email, String phone){
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
