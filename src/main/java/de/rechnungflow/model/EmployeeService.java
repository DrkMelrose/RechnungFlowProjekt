package de.rechnungflow.model;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService {

    private int nextId = 1;
    private final List<Employee> employees = new ArrayList<>();

    public Employee createEmployee(String name){
        Employee employee = new Employee(nextId++, name);
        employees.add(employee);
        return employee;
    }

    public Employee findEmployeeById(int id){
        return employees.stream()
                .filter(e -> e.getEmployeesId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Employee> getAll(){
        return new ArrayList<>(employees);
    }
}
