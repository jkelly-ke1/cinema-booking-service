package com.cinemabooking.controllers;

import com.cinemabooking.dto.EmployeeDto;
import com.cinemabooking.models.Employee;
import com.cinemabooking.services.SecurityService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class SecurityController {

    private final SecurityService securityService;
    private final ModelMapper modelMapper;

    public SecurityController(SecurityService securityService, ModelMapper modelMapper) {
        this.securityService = securityService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerNewEmployee(@RequestBody EmployeeDto employeeDto) {
        securityService.registerEmployee(convertFromEmployeeDto(employeeDto));
        return ResponseEntity.ok("New employee was registered!");
    }

    @GetMapping("/login")
    public ResponseEntity<?> loginEmployee(@AuthenticationPrincipal EmployeeDto employeeDto) {
        return ResponseEntity.ok("Login success!");
    }

    private Employee convertFromEmployeeDto(EmployeeDto employeeDto) {
        return modelMapper.map(employeeDto, Employee.class);
    }

}
