package com.cinemabooking.controllers;

import com.cinemabooking.dto.SeatDto;
import com.cinemabooking.models.Seat;
import com.cinemabooking.services.SeatService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class SeatController {

    private final SeatService seatService;
    private final ModelMapper modelMapper;

    @Autowired
    public SeatController(SeatService seatService, ModelMapper modelMapper) {
        this.seatService = seatService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add-seat")
    public void addSeat(@RequestBody SeatDto seatDto) {
        seatService.addSeat(convertFromSeatDto(seatDto));
    }

    private SeatDto convertToSeatDto(Seat seat) {
        return modelMapper.map(seat, SeatDto.class);
    }

    private Seat convertFromSeatDto(SeatDto seatDto) {
        return modelMapper.map(seatDto, Seat.class);
    }

}
