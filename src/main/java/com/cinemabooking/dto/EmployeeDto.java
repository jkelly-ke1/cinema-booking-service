package com.cinemabooking.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class EmployeeDto {

    @NotNull
    @Size(min = 3, max = 30, message = "Username must contain from 4 to 30 characters.")
    private String username;

    @NotNull
    @Size(min = 3)
    private String password;

}
