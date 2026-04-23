package com.rechnung_flow.RechnungFlowService.repositories;

import com.rechnung_flow.RechnungFlowService.model.enteties.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository  extends JpaRepository<Employee, Long> {
}
