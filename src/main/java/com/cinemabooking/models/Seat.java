package com.cinemabooking.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "seat")
public class Seat {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "row")
    private int row;

    @Column(name = "place")
    private int place;

    @Column(name = "hall_number")
    private int hallNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "assign_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date assignDate;

    @Column(name = "session_time")
    private LocalDateTime sessionTime;

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", row=" + row +
                ", place=" + place +
                ", hallNumber=" + hallNumber +
                ", user=" + user +
                ", assignDate=" + assignDate +
                '}';
    }
}
