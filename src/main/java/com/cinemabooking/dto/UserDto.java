package com.cinemabooking.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.*;

@Getter
@Setter
public class UserDto {

    @Column(name = "full_name")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+",
            message = "Name should be in this format: 'Name Surname'")
    @NotBlank(message = "Cannot be blank!")
    @Size(min = 6, max = 40, message = "Name must contain 6 to 40 characters.")
    private String fullName;

}
