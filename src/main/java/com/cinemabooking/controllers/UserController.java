package com.cinemabooking.controllers;

import com.cinemabooking.dto.UserDto;
import com.cinemabooking.models.User;
import com.cinemabooking.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("user/")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add")
    public void addUser(@Valid @RequestBody UserDto userDto) {
        userService.addUser(convertFromUserDto(userDto));
    }

    @GetMapping("/get")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/get/{id}")
    public User getUserById(@PathVariable long id) {
        return userService.getUserById(id).get();
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody UserDto userDto) {
        userService.updateUser(id, convertFromUserDto(userDto));
        return ResponseEntity.ok("User by id " + id + " was updated.");
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable long id) {
        userService.deleteUser(id);
    }

    // remove before push next time 
    @PostMapping("/add-bare")
    public void addBareUser(@RequestBody User user) {
        userService.addUser(user);
    }

    private User convertFromUserDto(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }



}
