package com.cinemabooking.services;

import com.cinemabooking.models.Seat;
import com.cinemabooking.models.User;
import com.cinemabooking.repositories.SeatRepository;
import com.cinemabooking.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private final UserRepository userRepository;

    @Autowired
    public SeatService(SeatRepository seatRepository, UserRepository userRepository) {
        this.seatRepository = seatRepository;
        this.userRepository = userRepository;
    }

    public void addSeat(Seat seat) {
        enrichSeat(seat);
        seatRepository.save(seat);
    }

    public void assignSeat(int id, User selectedUser) {
        seatRepository.findById(id).ifPresent(
                seat -> {
                    seat.setUser(selectedUser);
                    seat.setExpirationDate(new Date());
                }
        );
    }

    private void enrichSeat(Seat seat) {
        seat.setExpirationDate(new Date());
        seat.setUser(userRepository.getUserByFullName(seat.getUser().getFullName()));
    }

}
