package com.cinemabooking.controllers;

import com.cinemabooking.models.Employee;
import com.cinemabooking.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final EmployeeService employeeService;

    @Autowired
    public AdminController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee/find-all")
    public List<Employee> findEmployeeList() {
        return employeeService.getAllEmployees();
    }

}
