package com.myproject.ReserveYourSpot.model;

import com.google.gson.annotations.Expose;
import org.springframework.validation.annotation.Validated;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

// Comment: general formatting
// Comment: .*; should not be used, import only what we need
// Comment: missing FK towards OfficeRoom
// Comment: use lombok annotations like @Getter, @Builder, @NoArgsConstructor, @AllArgsConstructor, @ToString, @EqualsAndHashCode
@Entity
@Validated
@Table(name = "stations")
public class WorkStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    // Comment: formatting, please use space between properties
    @Expose
    // Comment: NotBlank annotation covers NotNull also so NotNull is obsolete
    @NotBlank
    @NotNull(message = "Every workStation must have it's unique code!")
    private String uniqueCode;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workStation", orphanRemoval = true)
    @Valid
    // Comment: why using collection interface instead of List interface?
    private Collection<Reservation> reservations = new ArrayList<>();

    public WorkStation(int id) {
        this.id = id;
    }

    public WorkStation(int id, String uniqueCode) {
        this.id = id;
        this.uniqueCode = uniqueCode;
    }

    // Comment: use lombok
    public WorkStation(int id, String uniqueCode, Collection<Reservation> reservations) {
        this.id = id;
        this.uniqueCode = uniqueCode;
        this.reservations = reservations;
    }

    // Comment: use lombok
    public WorkStation() {
    }

    // Comment: use lombok
    // Comment: getters and setters go in pair like getX, detX, getY, setY
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

    // Advice: good practice is to omit id (PK) from toString method
    @Override
    public String toString() {
        return "WorkStation{" +
                "id=" + id +
                ", uniqueCode='" + uniqueCode + '\'' +
                ", reservations=" + reservations +
                '}';
    }
}
