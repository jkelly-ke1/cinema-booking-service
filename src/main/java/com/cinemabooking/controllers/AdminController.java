package com.cinemabooking.controllers;

import com.cinemabooking.dto.EmployeeDto;
import com.cinemabooking.models.Employee;
import com.cinemabooking.services.EmployeeService;
import com.cinemabooking.services.SecurityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final EmployeeService employeeService;
    private final SecurityService securityService;
    private final ModelMapper modelMapper;

    @Autowired
    public AdminController(EmployeeService employeeService, SecurityService securityService, ModelMapper modelMapper) {
        this.employeeService = employeeService;
        this.securityService = securityService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/employee/find/all")
    public List<Employee> findEmployeeList() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/employee/find/{id}")
    public ResponseEntity<?> findEmployee(@PathVariable int id) {
        if (employeeService.getEmployeeById(id).isEmpty()) {
            return new ResponseEntity<>("User by id " + id + " is not found.",
                    HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PostMapping("/employee/add")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDto employeeDto) {
        securityService.registerEmployee(convertFromEmployeeDto(employeeDto));
        return ResponseEntity.ok("New employee was created.");
    }

    @DeleteMapping("/employee/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") int id) {
        if (employeeService.getEmployeeById(id).isEmpty()) {
            return new ResponseEntity<>("User by id " + id + " is not found.",
                    HttpStatus.BAD_REQUEST);
        }
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("User was deleted.");
    }

    @PatchMapping("/employee/edit/{id}")
    public ResponseEntity<?> editEmployee(@PathVariable("id") int id,
                                          @RequestBody EmployeeDto employeeDto) {
        if (employeeService.getEmployeeById(id).isEmpty()) {
            return new ResponseEntity<>("User by id " + id + " is not found.",
                    HttpStatus.BAD_REQUEST);
        }

        securityService.registerEmployee(convertFromEmployeeDto(employeeDto));
        return ResponseEntity.ok("Employee was edited.");

    }

    private Employee convertFromEmployeeDto(EmployeeDto employeeDto) {
        return modelMapper.map(employeeDto, Employee.class);
    }

}
