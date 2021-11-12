package com.pluralsight.reserve_your_spot.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import java.util.Date;

@Entity
@AllArgsConstructor
@Table(name = "reservations")
public class Reservation{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservation_Id;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @FutureOrPresent(message = "The arrival date must be today or in the future.")
    private Date date;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonIgnoreProperties("reservations")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonIgnoreProperties("reservations")
    @JoinColumn(name = "workStation_id")
    private WorkStation workStation;

    public Reservation(Date date, int id, int workStationId) {
    }

    public Reservation() {
    }

    public Reservation(Date date, User user, WorkStation workStation) {
        this.workStation = workStation;
    }

    public Reservation(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getReservation_Id() {
       return this.reservation_Id;
    }

    public Date getDate() {
        return this.date;
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

}


