package com.cinemabooking.repositories;

import com.cinemabooking.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {
    List<Seat> findAllBySessionTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Seat> findAllByHallNumber(int hallNumber);
}
