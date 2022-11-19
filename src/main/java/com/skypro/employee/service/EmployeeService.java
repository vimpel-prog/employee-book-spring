package com.skypro.employee.service;

import com.skypro.employee.model.Employee;
import com.skypro.employee.record.EmployeeRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final Map<Integer, Employee> employees = new HashMap<>();

    public int getAllSalary() {
        return employees.values().stream()
                .mapToInt(Employee::getSalary)
                .sum();
    }

    public Collection<Employee> getAllEmployees() {
        return employees.values();
    }

    public Employee addEmployee(EmployeeRequest employeeRequest) {
        if (employeeRequest.getFirstName() == null || employeeRequest.getLastName() == null) {
            throw new IllegalArgumentException("Employee name should be set");
        }
        Employee employee = new Employee(employeeRequest.getFirstName(),
                employeeRequest.getLastName(),
                employeeRequest.getDepartment(),
                employeeRequest.getSalary());
        employees.put(employee.getId(), employee);
        return employee;
    }

    public Employee getEmployeeMinSalary() {
        return employees.values().stream()
                .min(new ComparatorEmployee())
                .get();
    }

    public Employee getEmployeeMaxSalary() {
        return employees.values().stream()
                .max(new ComparatorEmployee())
                .get();
    }

    public Collection<Employee> getHighSalaryEmployees() {
        int allSalary=employees.values().stream()
                .mapToInt(Employee::getSalary)
                .sum();
        return employees.values().stream()
                .filter(employee -> employee.getSalary()>allSalary/ employees.size())
                .collect(Collectors.toList());
    }
}
