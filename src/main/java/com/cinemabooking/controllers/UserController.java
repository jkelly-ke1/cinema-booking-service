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
    private final UserValidator userValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService,
                          ModelMapper modelMapper,
                          UserValidator userValidator) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.userValidator = userValidator;
    }

    @PostMapping("/add")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto,
                                     BindingResult bindingResult) {
        var userToAdd = convertFromUserDto(userDto);
        userValidator.validate(userToAdd, bindingResult);
        userService.addUser(userToAdd);

        return ResponseEntity.ok("New user was created.");
    }

    @GetMapping("/get-all")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}/get")
    public User getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody @Valid UserDto userDto) {
        userService.updateUser(id, convertFromUserDto(userDto));
        return ResponseEntity.ok("User by id " + id + " was updated.");
    }

    @DeleteMapping("/{id}/delete")
    public void deleteUserById(@PathVariable long id) {
        userService.deleteUser(id);
    }

    private User convertFromUserDto(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handlerException(UserNotFoundException e) {
        var response = new UserErrorResponse("User with this id was not found.", System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}