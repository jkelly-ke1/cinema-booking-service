package com.cinemabooking.controllers;

import com.cinemabooking.dto.UserDto;
import com.cinemabooking.models.User;
import com.cinemabooking.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/add-user")
    public void addUser(@RequestBody UserDto userDto) {
        userService.addUser(convertFromUserDto(userDto));
    }

    // remove before push next time 
    @PostMapping("/bare-user")
    public void addBareUser(@RequestBody User user) {
        userService.addUser(user);
    }

    private User convertFromUserDto(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }



}
