package com.cinemabooking.services;

import com.cinemabooking.models.User;
import com.cinemabooking.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void addUser(User user) {
        enrichUser(user);
        userRepository.save(user);
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public User findUserByName(String fullName) {
        return userRepository.getUserByFullName(fullName);
    }

    private void enrichUser(User user) {
        user.setRegisteredAt(new Date());
    }

}
