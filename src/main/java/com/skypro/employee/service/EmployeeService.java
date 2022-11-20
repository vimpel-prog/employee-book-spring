package com.skypro.employee.service;

import com.skypro.employee.Exception.InvalidEmployeeArgumentException;
import com.skypro.employee.model.Employee;
import com.skypro.employee.record.EmployeeRequest;
import org.apache.commons.lang3.StringUtils;
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
        if (!StringUtils.isAlpha(employeeRequest.getFirstName()) || !StringUtils.isAlpha(employeeRequest.getLastName())) {
            throw new InvalidEmployeeArgumentException();
        }
        Employee employee = new Employee(StringUtils.capitalize(employeeRequest.getFirstName()),
                StringUtils.capitalize(employeeRequest.getLastName()),
                employeeRequest.getDepartment(),
                employeeRequest.getSalary());
        employees.put(employee.getId(), employee);
        return employee;
    }

    public Employee getEmployeeMinSalary() {
        return employees.values().stream()
                .min(new ComparatorEmployee())
                .orElseThrow();
    }

    public Employee getEmployeeMaxSalary() {
        return employees.values().stream()
                .max(new ComparatorEmployee())
                .orElseThrow();
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
