package com.myproject.ReserveYourSpot.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class Reservation{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservation_Id;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @FutureOrPresent(message = "The arrival date must be today or in the future.")
    @NotNull
    private LocalDate date;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonIgnoreProperties("reservations")
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonIgnoreProperties("reservations")
    @JoinColumn(name = "workStation_id")
    @NotNull
    private WorkStation workStation;

    public Reservation(LocalDate date, int id, int workStationId) {
    }

    public Reservation() {
    }

    public Reservation(int reservation_Id, LocalDate date, User user, WorkStation workStation) {
        this.reservation_Id = reservation_Id;
        this.date = date;
        this.user = user;
        this.workStation = workStation;
    }

    public Reservation(LocalDate date, User user, WorkStation workStation) {
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

    public LocalDate getDate() {
        return date;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public void setDate(LocalDate date) {
        this.date = date;
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


}


