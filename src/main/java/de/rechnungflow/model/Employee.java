package de.rechnungflow.model;

public class Employee {
    private int id;
    private String name;

    public Employee(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getEmployeesId(){
        return id;
    }
}
