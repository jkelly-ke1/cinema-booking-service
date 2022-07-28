package com.cinemabooking.repositories;

import com.cinemabooking.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {
    List<Seat> findAllByExpirationDateBetween(Date startDate, Date endDate);
    List<Seat> findAllByHallNumber(int hallNumber);
}
