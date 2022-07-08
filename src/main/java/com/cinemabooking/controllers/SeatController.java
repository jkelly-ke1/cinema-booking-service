package com.cinemabooking.controllers;

import com.cinemabooking.models.Seat;
import com.cinemabooking.services.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class SeatController {

    private final SeatService seatService;

    @Autowired
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @PostMapping("/add-seat")
    public void addSeat(@RequestBody Seat seat) {
        seatService.addSeat(seat);
    }


}
