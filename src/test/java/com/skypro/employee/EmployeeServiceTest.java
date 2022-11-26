package com.skypro.employee;

import com.skypro.employee.model.Employee;
import com.skypro.employee.record.EmployeeRequest;
import com.skypro.employee.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeServiceTest {
    private EmployeeService employeeService;

    @BeforeEach
    public void fillTestValues() {
        employeeService = new EmployeeService();
        Stream.of(
                new EmployeeRequest("TestI", "TestI", 1, 4500),
                new EmployeeRequest("TestII", "TestII", 1, 5800),
                new EmployeeRequest("TestIII", "TestIII", 2, 3200),
                new EmployeeRequest("TestIV", "TestIV", 2, 2800),
                new EmployeeRequest("TestV", "TestV", 3, 7600)
        ).forEach(employeeService::addEmployee);
    }

    @Test
    public void addEmployee() {
        EmployeeRequest request = new EmployeeRequest(
                "Value", "Value", 3, 5950);
        Employee result = employeeService.addEmployee(request);
        assertEquals(request.getFirstName(), result.getFirstName());
        assertEquals(request.getLastName(), result.getLastName());
        assertEquals(request.getDepartment(), result.getDepartment());
        assertEquals(request.getSalary(), result.getSalary());

        assertThat(employeeService.getAllEmployees()).contains(result);
    }

    @Test
    public void listEmployees() {
        Collection<Employee> employees = employeeService.getAllEmployees();
        assertThat(employees).hasSize(5);
        assertThat(employees).last()
                .extracting(Employee::getLastName)
                .isEqualTo("TestV");
    }

    @Test
    public void getSumOfSalaries() {
        int sum = employeeService.getAllSalary();
        assertThat(sum).isEqualTo(23900);
    }

    @Test
    public void getEmployeeWithMinSalary() {
        Employee employee = employeeService.getEmployeeMinSalary();
        assertThat(employee)
                .extracting(Employee::getFirstName)
                .isNotNull()
                .isEqualTo("TestIV");
    }

    @Test
    public void getEmployeeWithMaxSalary() {
        Employee employee = employeeService.getEmployeeMaxSalary();
        assertThat(employee)
                .extracting(Employee::getFirstName)
                .isNotNull()
                .isEqualTo("TestV");
    }

    @Test
    public void removeEmployee() {
        employeeService.removeEmployee(employeeService.getAllEmployees().iterator().next().getId());
        Collection<Employee> employees = employeeService.getAllEmployees();
        assertThat(employees).hasSize(4);
    }

    @Test
    public void addEmployeeWithCapitalize() {
        Employee employee = employeeService.addEmployee(
                new EmployeeRequest("inTest", "inTest", 3, 10000));
        assertThat(employee).extracting(Employee::getFirstName)
                .isEqualTo("InTest");
        assertThat(employee).extracting(Employee::getLastName)
                .isEqualTo("InTest");
    }

    @Test
    public void getListEmployeesWithHighSalary() {
        Collection<Employee> employees = employeeService.getHighSalaryEmployees();
        assertThat(employees).hasSize(2);
        Employee employee1 = new Employee("TestII", "TestII", 1, 5800);
        Employee employee2 = new Employee("TestV", "TestV", 3, 7600);

        assertThat(employees).contains(employee1);
        assertThat(employees).contains(employee2);
    }

}
