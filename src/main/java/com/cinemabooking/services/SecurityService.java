package com.cinemabooking.services;

import com.cinemabooking.models.Employee;
import com.cinemabooking.repositories.EmployeeRepository;
import com.cinemabooking.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void updateEmployee(Employee updatedEmployee, int id) {
        var employeeToBeUpdate = employeeRepository.findById(id).get();
        employeeToBeUpdate.setUsername(updatedEmployee.getUsername());
        employeeToBeUpdate.setPassword(passwordEncoder.encode(updatedEmployee.getPassword()));
        employeeRepository.save(employeeToBeUpdate);
    }

}
