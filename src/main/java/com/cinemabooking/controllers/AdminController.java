package com.cinemabooking.controllers;

import com.cinemabooking.dto.EmployeeDto;
import com.cinemabooking.models.Employee;
import com.cinemabooking.services.EmployeeService;
import com.cinemabooking.services.SecurityService;
import com.cinemabooking.util.EmployeeErrorResponse;
import com.cinemabooking.util.EmployeeNotFoundException;
import com.cinemabooking.util.EmployeeValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final EmployeeService employeeService;
    private final SecurityService securityService;
    private final EmployeeValidator employeeValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public AdminController(EmployeeService employeeService,
                           SecurityService securityService,
                           EmployeeValidator employeeValidator,
                           ModelMapper modelMapper) {
        this.employeeService = employeeService;
        this.securityService = securityService;
        this.employeeValidator = employeeValidator;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/employee/find/all")
    public ResponseEntity<?> findEmployeeList() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/employee/find/{id}")
    public ResponseEntity<?> findEmployee(@PathVariable int id) {
        if (employeeService.getEmployeeById(id).isEmpty())
            return new ResponseEntity<>("User by id " + id + " not found.", HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PostMapping("/employee/add")
    public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeDto employeeDto,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity<>("Validation error.", HttpStatus.BAD_REQUEST);

        var employeeToCreate = convertFromEmployeeDto(employeeDto);
        employeeValidator.validate(employeeToCreate, bindingResult);

        securityService.registerEmployee(employeeToCreate);
        return ResponseEntity.ok("New employee was created.");
    }

    @DeleteMapping("/employee/{id}/delete")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") int id) {
        if (employeeService.getEmployeeById(id).isEmpty())
            return new ResponseEntity<>("User by id " + id + " not found.", HttpStatus.BAD_REQUEST);

        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("User was deleted.");
    }

    @PatchMapping("/employee/{id}/edit")
    public ResponseEntity<?> editEmployee(@PathVariable("id") int id,
                                          @RequestBody EmployeeDto employeeDto) {
        if (employeeService.getEmployeeById(id).isEmpty())
            return new ResponseEntity<>("User by id " + id + " not found.", HttpStatus.BAD_REQUEST);

        securityService.updateEmployee(convertFromEmployeeDto(employeeDto), id);
        return ResponseEntity.ok("Employee was edited.");
    }

    @ExceptionHandler
    private ResponseEntity<EmployeeErrorResponse> handlerExceptions(EmployeeNotFoundException e) {
        var response = new EmployeeErrorResponse("Employee by this id not exist.", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    private Employee convertFromEmployeeDto(EmployeeDto employeeDto) {
        return modelMapper.map(employeeDto, Employee.class);
    }

}
