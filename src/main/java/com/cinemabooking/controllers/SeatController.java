package com.cinemabooking.controllers;

import com.cinemabooking.dto.DateDto;
import com.cinemabooking.dto.SeatDto;
import com.cinemabooking.models.Seat;
import com.cinemabooking.models.User;
import com.cinemabooking.services.SeatService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/seat")
public class SeatController {

    private final SeatService seatService;
    private final ModelMapper modelMapper;

    @Autowired
    public SeatController(SeatService seatService, ModelMapper modelMapper) {
        this.seatService = seatService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add")
    public void addSeat(@RequestBody SeatDto seatDto) {
        seatService.addSeat(convertFromSeatDto(seatDto));
    }

    // cinema session should be created
    // with client-side parameters (hall number, max rows, etc.)
    @PostMapping("/new-session")
    public void createBlankCinemaSession(@RequestBody SeatDto seatDto) {
        seatService.makeBlankSeats(convertFromSeatDto(seatDto));
    }


    @GetMapping("/find-all")
    public List<SeatDto> getAllSeats() {
        return seatService.getAllSeats().stream().map(this::convertToSeatDto)
                .collect(Collectors.toList());
    }


    @GetMapping("/find-by-date")
    public List<Seat> getSeatsByLocalDateTime(@RequestBody DateDto dateDto) {
        var first = dateDto.getStart();
        var second = dateDto.getEnd();

        return seatService.getSeatsByLocalDateTime(first, second);
    }

    @GetMapping("/find-by-hall/{hallNumber}")
    public ResponseEntity<?> getSeatsByHall(@PathVariable int hallNumber) {
        if (seatService.getSeatsByHallNumber(hallNumber).isEmpty())
            return new ResponseEntity<>("Hall by number " + hallNumber + " was not found.", HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(seatService.getSeatsByHallNumber(hallNumber));
    }

    @PatchMapping("/{id}/reserve")
    public ResponseEntity<?> reserveSeat(@RequestBody SeatDto seatDto,
                                         @PathVariable("id") int id) {
        if (seatService.getSeatById(id).get().getUser() != null)
            return ResponseEntity.ok("Seat by id: " + id + " is already reserved.");

        var assignedUser = new User();
        assignedUser.setFullName(seatDto.getUser().getFullName());
        seatService.assignSeat(id, assignedUser);
        return ResponseEntity.ok("Seat with id: " + id + " was reserved.");
    }

    @PatchMapping("/{id}/release")
    public ResponseEntity<?> releaseSeat(@PathVariable("id") int id) {
        seatService.releaseSeat(id);
        return ResponseEntity.ok("Seat by id: " + id + " was released.");
    }

    private SeatDto convertToSeatDto(Seat seat) {
        return modelMapper.map(seat, SeatDto.class);
    }

    private Seat convertFromSeatDto(SeatDto seatDto) {
        return modelMapper.map(seatDto, Seat.class);
    }

}
