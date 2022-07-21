package com.cinemabooking.services;

import com.cinemabooking.models.User;
import com.cinemabooking.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
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

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(long id) {
        return userRepository.getUserById(id);
    }

    public User findUserByName(String fullName) {
        return userRepository.getUserByFullName(fullName);
    }

    @Transactional
    public void updateUser(long id, User updatedUser) {
        userRepository.getUserById(id).ifPresent(
                user -> {
                    user.setFullName(updatedUser.getFullName());
                }
        );
    }

    @Transactional
    public void deleteUser(long id) {
        userRepository.deleteUserById(id);
    }

    private void enrichUser(User user) {
        user.setRegisteredAt(new Date());
    }

}
