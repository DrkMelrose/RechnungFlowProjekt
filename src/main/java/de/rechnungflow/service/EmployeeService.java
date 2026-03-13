package de.rechnungflow.service;

import com.fasterxml.jackson.core.type.TypeReference;
import de.rechnungflow.model.Employee;
import de.rechnungflow.persistance.JsonStorage;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {

    private static final Path FILE_PATH = Path.of("data", "employees.json");
    private final JsonStorage storage = new JsonStorage();
    private final Path filePath;
    private int nextId = 1;
    private final List<Employee> employees = new ArrayList<>();

    public EmployeeService(Path filePath){
        this.filePath = filePath;
        loadFromFile();
    }

    public EmployeeService(){
        this(Paths.get("data/employees,json"));
    }

    public void loadFromFile(){
        List <Employee> loaded = storage.readList(filePath, new TypeReference<List<Employee>>(){});
        employees.clear();
        employees.addAll(loaded);

        nextId = employees.stream()
                .mapToInt(Employee::getEmployeesId)
                .max()
                .orElse(0)+1;
    }

    public void saveToFile(){
        storage.writeList(filePath, employees);
    }

    public Employee createEmployee(String name, String phone, String email){
        Employee employee = new Employee(nextId++, name, phone, email);
        employees.add(employee);
        saveToFile();
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

    public void add(Employee employee){
        employees.add(employee);
    }

}
