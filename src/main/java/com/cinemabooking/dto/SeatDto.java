package com.cinemabooking.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SeatDto {

    private int row;

    private int place;

    private int hallNumber;

    private LocalDateTime sessionTime;

    private UserDto user;

}
