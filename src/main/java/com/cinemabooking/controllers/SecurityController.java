package com.cinemabooking.controllers;

import com.cinemabooking.dto.EmployeeDto;
import com.cinemabooking.models.Employee;
import com.cinemabooking.services.SecurityService;
import com.cinemabooking.util.EmployeeValidator;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/auth")
public class SecurityController {

    private final SecurityService securityService;
    private final EmployeeValidator employeeValidator;
    private final ModelMapper modelMapper;

    public SecurityController(SecurityService securityService,
                              EmployeeValidator employeeValidator,
                              ModelMapper modelMapper) {
        this.securityService = securityService;
        this.employeeValidator = employeeValidator;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerNewEmployee(@Valid @RequestBody EmployeeDto employeeDto,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity<>("Validation error.", HttpStatus.BAD_REQUEST);

        var employeeToRegister = convertFromEmployeeDto(employeeDto);
        employeeValidator.validate(employeeToRegister, bindingResult);
        securityService.registerEmployee(employeeToRegister);
        return ResponseEntity.ok("New employee was registered!");
    }

    private Employee convertFromEmployeeDto(EmployeeDto employeeDto) {
        return modelMapper.map(employeeDto, Employee.class);
    }

}
