package com.cinemabooking.services;

import com.cinemabooking.models.Employee;
import com.cinemabooking.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SecurityService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerEmployee(Employee employee) {
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
//        employee.setRoles("ROLE_EMPLOYEE");
        employeeRepository.save(employee);
    }

}
