package com.myproject.ReserveYourSpot.model;

import com.google.gson.annotations.Expose;
import org.springframework.validation.annotation.Validated;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Validated
@Table(name = "stations")
public class WorkStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Expose
    @NotBlank
    @NotNull(message = "Every workStation must have it's unique code!")
    private String uniqueCode;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workStation", orphanRemoval = true)
    @Valid
    private Collection<Reservation> reservations = new ArrayList<>();

    public WorkStation(int id) {
        this.id = id;
    }

    public WorkStation(int id, String uniqueCode) {
        this.id = id;
        this.uniqueCode = uniqueCode;
    }

    public WorkStation(int id, String uniqueCode, Collection<Reservation> reservations) {
        this.id = id;
        this.uniqueCode = uniqueCode;
        this.reservations = reservations;
    }

    public WorkStation() {
    }

    public int getId() {
        return this.id;
    }

    public String getUniqueCode() {
        return this.uniqueCode;
    }

    public Collection<Reservation> getReservations() {
        return this.reservations;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public void setReservations(Collection<Reservation> reservations) {
        this.reservations = reservations;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof WorkStation;
    }

    @Override
    public String toString() {
        return "WorkStation{" +
                "id=" + id +
                ", uniqueCode='" + uniqueCode + '\'' +
                ", reservations=" + reservations +
                '}';
    }
}
