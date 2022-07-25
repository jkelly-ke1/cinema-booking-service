package com.cinemabooking.controllers;

import com.cinemabooking.dto.UserDto;
import com.cinemabooking.models.User;
import com.cinemabooking.services.UserService;
import com.cinemabooking.util.UserErrorResponse;
import com.cinemabooking.util.UserNotFoundException;
import com.cinemabooking.util.UserValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("user/")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final UserValidator userValidator;


    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, UserValidator userValidator) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.userValidator = userValidator;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@Valid @RequestBody UserDto userDto,
                                     BindingResult bindingResult) {
        User userToAdd = convertFromUserDto(userDto);
        userValidator.validate(userToAdd, bindingResult);
        userService.addUser(userToAdd);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/get")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/get/{id}")
    public User getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody @Valid UserDto userDto) {
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

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handlerException(UserNotFoundException e) {
        UserErrorResponse response = new UserErrorResponse(
                "User with this id was not found.",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


}
