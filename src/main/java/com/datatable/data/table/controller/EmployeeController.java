package com.datatable.data.table.controller;

import com.datatable.data.table.dto.BulkEmployeeRequest;
import com.datatable.data.table.model.Employee;
import com.datatable.data.table.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
        return ResponseEntity.ok(employee);
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        Employee updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/bulk")
    public ResponseEntity<Void> handleBulkOperations(@RequestBody BulkEmployeeRequest request) {
        if (request.getCreate() != null) {
            employeeService.saveAllEmployees(request.getCreate());
        }
        if (request.getUpdate() != null) {
            for (Employee employee : request.getUpdate()) {
                employeeService.updateEmployee(employee.getId(), employee);
            }
        }
        if (request.getDelete() != null) {
            employeeService.deleteEmployees(request.getDelete());
        }
        return ResponseEntity.ok().build();
    }
}
