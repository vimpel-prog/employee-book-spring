package com.skypro.employee;

import com.skypro.employee.Exception.EmployeeNotFoundException;
import com.skypro.employee.model.Employee;
import com.skypro.employee.service.DepartmentService;
import com.skypro.employee.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {
    private final List<Employee> employees = List.of(
            new Employee("TestI", "TestI", 1, 4500),
                new Employee("TestII", "TestII", 1, 5800),
                new Employee("TestIII", "TestIII", 2, 3200),
                new Employee("TestIV", "TestIV", 2, 2800),
                new Employee("TestV", "TestV", 3, 7600));
    @Mock
    EmployeeService employeeService;
    @InjectMocks
    DepartmentService departmentService;
    @BeforeEach
    void setUp(){
        when(employeeService.getAllEmployees())
                .thenReturn(employees);
    }
    @Test
    void getEmployeesByDepartment(){
        Collection<Employee> employeeList = departmentService.getDepartmentEmployees(1);
        assertThat(employeeList)
                .hasSize(2)
                .contains(employees.get(0),
                        employees.get(1));
    }
    @Test
    void sumOfSalariesByDepartment(){
        int salarySum = departmentService.getAllSalaryByDepartment(1);
        assertEquals(10_300,salarySum);
    }
    @Test
    void maxSalaryByDepartment(){
        int max = departmentService.getMaxSalaryByDepartment(2);
        assertThat(max).isEqualTo(3200);
    }
    @Test
    void minSalaryByDepartment(){
        int min = departmentService.getMinSalaryByDepartment(2);
        assertThat(min).isEqualTo(2800);
    }
    @Test
    void groupedEmployeesByDepartment(){
        Map<Integer,List<Employee>> groupedEmployees = departmentService
                .getEmployeesByDepartment();
         assertThat(groupedEmployees)
                 .hasSize(3)
                 .containsEntry(1,
                         List.of(employees.get(0),employees.get(1)))
                 .containsEntry(2,
                         List.of(employees.get(2),employees.get(3)))
                 .containsEntry(3,
                         List.of(employees.get(4))
                 );
    }
    @Test
    void WhenNoEmployeesThenGroupByReturnEmptyMap(){
         when(employeeService.getAllEmployees()).thenReturn(List.of());
        Map<Integer,List<Employee>> groupedEmployees = departmentService
                .getEmployeesByDepartment();
        assertThat(groupedEmployees).isEmpty();
    }
    @Test
    void whenNoEmployeesThenMinSalaryInDepartmentThrowsException(){
        when(employeeService.getAllEmployees()).thenReturn(List.of());
        assertThatThrownBy(()->departmentService.getMinSalaryByDepartment(0))
                .isInstanceOf(EmployeeNotFoundException.class);
    }
}
