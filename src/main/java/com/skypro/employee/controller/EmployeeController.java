package com.skypro.employee.controller;

import com.skypro.employee.model.Employee;
import com.skypro.employee.record.EmployeeRequest;
import com.skypro.employee.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("employees")
    public Collection<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping("employees")
    public Employee createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        return employeeService.addEmployee(employeeRequest);
    }

    @GetMapping("employees/salary/sum")
    public int getAllSalary(){
        return employeeService.getAllSalary();
    }

    @GetMapping("employee/salary/min")
    public Employee getEmployeeMinSalary(){
        return employeeService.getEmployeeMinSalary();
    }

    @GetMapping("employee/salary/max")
    public Employee getEmployeeMaxSalary(){
        return employeeService.getEmployeeMaxSalary();
    }

    @GetMapping("employee/high-salary")
    public Collection<Employee> getHighSalaryEmployees(){
        return employeeService.getHighSalaryEmployees();
    }
}
