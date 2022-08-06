package com.cinemabooking.services;

import com.cinemabooking.models.Employee;
import com.cinemabooking.models.Role;
import com.cinemabooking.repositories.EmployeeRepository;
import com.cinemabooking.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class SecurityService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public SecurityService(EmployeeRepository employeeRepository,
                           PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    //every new employee have 'EMPLOYEE' role by default
    @Transactional
    public void registerEmployee(Employee employee) {
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employee.setRoles(roleRepository.findRoleById(2));
        employeeRepository.save(employee);
    }



}
