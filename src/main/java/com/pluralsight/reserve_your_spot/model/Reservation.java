package com.pluralsight.reserve_your_spot.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pluralsight.reserve_your_spot.exception.UniqueDate;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "reservations")
public class Reservation{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservation_Id;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    @NotNull(message = "This field must have date value!")
    @FutureOrPresent(message = "The arrival date must be today or in the future.")
    @UniqueDate
    private Date date;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonIgnoreProperties("reservations")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonIgnoreProperties("reservations")
    @JoinColumn(name = "workStation_id")
    private WorkStation workStation;

    public Reservation(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Reservation(int reservation_Id, Date date) {
        this.reservation_Id = reservation_Id;
        this.date = date;
    }

    public Reservation() {
    }

    public int getReservation_Id() {
        return this.reservation_Id;
    }

    public Date getDate() {
        return this.date;
    }

    public Reservation(WorkStation workStation) {
        this.workStation = workStation;
    }

    public WorkStation getWorkStation() {
        return workStation;
    }

    public void setWorkStation(WorkStation workStation) {
        this.workStation = workStation;
    }

    public void setReservation_Id(int reservation_Id) {
        this.reservation_Id = reservation_Id;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public void setDate(Date date) {
        this.date = date;
    }


    protected boolean canEqual(final Object other) {
        return other instanceof Reservation;
    }


}


