package com.cinemabooking.services;

import com.cinemabooking.models.Seat;
import com.cinemabooking.models.User;
import com.cinemabooking.repositories.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(readOnly = true)
public class SeatService {

    private final SeatRepository seatRepository;

    @Autowired
    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }


    @Transactional
    public void addSeat(Seat seat) {
        seatRepository.save(seat);
    }

    @Transactional
    public void assignSeat(int id, User selectedUser) {
        seatRepository.findById(id).ifPresent(
                seat -> {
                    seat.setUser(selectedUser);
                    seat.setExpirationDate(new Date());
                }
        );
    }

}
