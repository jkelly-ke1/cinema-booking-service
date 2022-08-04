package com.cinemabooking.repositories;

import com.cinemabooking.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Set<Role> findRoleById(int id);
}
