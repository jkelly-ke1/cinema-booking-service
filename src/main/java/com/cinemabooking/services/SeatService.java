package com.cinemabooking.services;

import com.cinemabooking.models.Seat;
import com.cinemabooking.models.User;
import com.cinemabooking.repositories.SeatRepository;
import com.cinemabooking.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class SeatService {

    private final SeatRepository seatRepository;
    private final UserRepository userRepository;

    @Autowired
    public SeatService(SeatRepository seatRepository, UserRepository userRepository) {
        this.seatRepository = seatRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void addSeat(Seat seat) {
        enrichSeat(seat);
        seatRepository.save(seat);
    }

    @Transactional
    public void makeBlankSeats(Seat seat) {
        seatRepository.save(seat);
    }

    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
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

    @Transactional
    public void releaseSeat(int id) {
        seatRepository.findById(id).ifPresent(
                seat -> {
                    seat.setUser(null);
                    seat.setExpirationDate(null);
                }
        );
    }

    private void enrichSeat(Seat seat) {
        seat.setExpirationDate(new Date());
        seat.setUser(userRepository.getUserByFullName(seat.getUser().getFullName()));
    }

}
