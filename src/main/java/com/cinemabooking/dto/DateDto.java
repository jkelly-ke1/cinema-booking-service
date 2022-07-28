package com.cinemabooking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class DateDto {

//    @Temporal(TemporalType.TIMESTAMP)
    private Date start;

//    @Temporal(TemporalType.TIMESTAMP)
    private Date end;


    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "DateDto{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
