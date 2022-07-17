package com.cinemabooking.dto;

import com.cinemabooking.models.Seat;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class UserDto {

    @Column(name = "full_name")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+",
            message = "Name should be in this format: 'Name Surname'")
    @NotEmpty(message = "Cannot be empty.")
    @Size(min = 6, max = 40, message = "Name must contain 6 to 40 characters.")
    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
