package com.cinemabooking.repositories;

import com.cinemabooking.models.Employee;
import com.cinemabooking.security.EmployeeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findEmployeeByUsername(String username);
    void deleteEmployeeById(int id);
}
