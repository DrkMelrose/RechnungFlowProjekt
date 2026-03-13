package RoundTripsTests;

import de.rechnungflow.model.Employee;
import de.rechnungflow.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeesServicePersistenceTest {
    @TempDir
    Path tempDir;

    @Test
    public void saveAndLoad_roadtrip_employeeService(){
        //GIVEN
        Path file = tempDir.resolve("employees.json");
        EmployeeService employeeService = new EmployeeService(file);

        Employee emp1 = new Employee(1, "AliceWeidel", "23882838", "aliceweidel@gmail.com");
        Employee emp2 = new Employee(2, "Bobby", "1283881", "boby@gmail.com");

        employeeService.add(emp1);
        employeeService.add(emp2);
        //WHEN
        employeeService.saveToFile();

        EmployeeService employeeService2 = new EmployeeService(file);

        //THEN
        assertEquals(2, employeeService2.getAll().size(), "The number of employers must be same");
        assertEquals("AliceWeidel", employeeService2.findEmployeeById(1).getNameOfEmployee());
        assertEquals("23882838", employeeService2.findEmployeeById(1).getPhoneOfEmployee());

        //test Id
        Employee created = employeeService2.createEmployee("Anna", "178271", "annaelene@gmail.com");
        assertEquals(3, created.getEmployeesId());
    }
}
