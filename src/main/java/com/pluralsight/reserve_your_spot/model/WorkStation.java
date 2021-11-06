package com.pluralsight.reserve_your_spot.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "stations")
public class WorkStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    @NotNull(message = "Every workStation must have it's unique code!")
    private String uniqueCode;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workStation", orphanRemoval = true)
    private Collection<Reservation> reservations = new ArrayList<>();

    public WorkStation(int id) {
        this.id = id;
    }

    public WorkStation(int id, String uniqueCode) {
        this.id = id;
        this.uniqueCode = uniqueCode;
    }

    public WorkStation() {
    }

    public int getId() {
        return this.id;
    }

    public @NotBlank String getUniqueCode() {
        return this.uniqueCode;
    }

    public Collection<Reservation> getReservations() {
        return this.reservations;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUniqueCode(@NotBlank String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public void setReservations(Collection<Reservation> reservations) {
        this.reservations = reservations;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof WorkStation;
    }

    public String toString() {
        return "WorkStation(id=" + this.getId() + ", uniqueCode=" + this.getUniqueCode() + ", reservations=" + this.getReservations() + ")";
    }
}
