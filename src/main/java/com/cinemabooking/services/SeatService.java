package com.cinemabooking.services;

import com.cinemabooking.models.Seat;
import com.cinemabooking.models.User;
import com.cinemabooking.repositories.SeatRepository;
import com.cinemabooking.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    Logger logger = LoggerFactory.getLogger(SeatService.class);

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

    public List<Seat> getSeatsByLocalDateTime(LocalDateTime start, LocalDateTime end) {
        return seatRepository.findAllBySessionTimeBetween(start, end);
    }

    public List<Seat> getSeatsByHallNumber(int hallNumber) {
        return seatRepository.findAllByHallNumber(hallNumber);
    }


    public Optional<Seat> getSeatById(int id) {
        return seatRepository.findById(id);
    }

    // This method assign seat to user. If user is not presented
    // in user's database, will be created new.
    // Not the best implementation, but works.
    @Transactional
    public void assignSeat(int id, User selectedUser) {

        if (userRepository.getUserByFullName(selectedUser.getFullName()) == null) {
            logger.warn("User not found. Creating new user with name: " + selectedUser.getFullName());
            selectedUser.setRegisteredAt(new Date());
            userRepository.save(selectedUser);
            mapSeat(id, userRepository.getUserByFullName(selectedUser.getFullName()));

        } else if (userRepository.getUserByFullName(selectedUser.getFullName()) != null) {
            mapSeat(id, userRepository.getUserByFullName(selectedUser.getFullName()));
        }
    }

    @Transactional
    public void releaseSeat(int id) {
        seatRepository.findById(id).ifPresent(
                seat -> {
                    seat.setUser(null);
                    seat.setAssignDate(null);
                }
        );
    }

    // expiration date should be assigned by user query
    // or already be assigned during the seat creation
    private void enrichSeat(Seat seat) {
        seat.setAssignDate(new Date());
        seat.setUser(userRepository.getUserByFullName(seat.getUser().getFullName()));
    }

    private void mapSeat(int id, User user) {
        seatRepository.findById(id).ifPresent(
                seat -> {
                    seat.setUser(user);
                    seat.setAssignDate(new Date());
                }
        );
    }

}
