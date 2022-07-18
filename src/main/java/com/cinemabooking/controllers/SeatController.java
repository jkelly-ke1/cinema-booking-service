package com.cinemabooking.controllers;

import com.cinemabooking.dto.SeatDto;
import com.cinemabooking.dto.UserDto;
import com.cinemabooking.models.Seat;
import com.cinemabooking.models.User;
import com.cinemabooking.services.SeatService;
import com.cinemabooking.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("")
public class SeatController {

    private final SeatService seatService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public SeatController(SeatService seatService, UserService userService, ModelMapper modelMapper) {
        this.seatService = seatService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/seat/add")
    public void addSeat(@RequestBody SeatDto seatDto) {
        seatService.addSeat(convertFromSeatDto(seatDto));
    }

    // cinema session should be created with client-side parameters
    @PostMapping("/seat/new-session")
    public void createBlankCinemaSession(@RequestBody SeatDto seatDto) {
        seatService.makeBlankSeats(convertFromSeatDto(seatDto));
    }

    @GetMapping("/seat/find-all")
    public List<SeatDto> getAllSeats() {
        return seatService.getAllSeats().stream().map(this::convertToSeatDto)
                .collect(Collectors.toList());
    }

    @PatchMapping("/seat/{id}/reserve")
    public ResponseEntity<?> reserveSeat(@RequestBody SeatDto seatDto,
                                         @PathVariable("id") int id) {

        if(seatService.getSeatById(id).get().getUser() != null) {
            return ResponseEntity.ok("Seat by id: " + id + " is already reserved.");
        }
            User assignedUser = new User();
            assignedUser.setFullName(seatDto.getUser().getFullName());
            seatService.assignSeat(id, assignedUser);
            return ResponseEntity.ok("Seat with id: " + id + " was reserved.");
    }

    @PatchMapping("/seat/{id}/release")
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
