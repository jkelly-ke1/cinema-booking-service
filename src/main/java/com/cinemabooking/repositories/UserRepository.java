package com.cinemabooking.repositories;

import com.cinemabooking.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User getUserByFullName(String fullName);
    Optional<User> getUserById(long id);
    void deleteUserById(long id);
}
