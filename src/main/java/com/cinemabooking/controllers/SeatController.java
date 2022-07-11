package com.cinemabooking.controllers;

import com.cinemabooking.dto.SeatDto;
import com.cinemabooking.models.Seat;
import com.cinemabooking.services.SeatService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/seats")
    public List<SeatDto> getAllSeats() {
        return seatService.getAllSeats().stream().map(this::convertToSeatDto)
                .collect(Collectors.toList());
    }


    private SeatDto convertToSeatDto(Seat seat) {
        return modelMapper.map(seat, SeatDto.class);
    }

    private Seat convertFromSeatDto(SeatDto seatDto) {
        return modelMapper.map(seatDto, Seat.class);
    }

}
