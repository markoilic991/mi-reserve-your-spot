package com.myproject.ReserveYourSpot.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
@Table(name = "users")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SerializedName("id")
    @Expose
    private int id;
    @NotBlank
    @NotNull(message = "User name must have a value!")
    @SerializedName("name")
    @Expose
    private String name;
    @NotNull(message = "Email must have a value!")
    @Email
    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private Collection<Reservation> reservations = new ArrayList<>();

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User() {
    }

    public int getId() {
        return this.id;
    }

    public @NotBlank String getName() {
        return this.name;
    }

    public @NotNull @Email String getEmail() {
        return this.email;
    }


    public Collection<Reservation> getReservations() {
        return this.reservations;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public void setEmail(@NotNull @Email String email) {
        this.email = email;
    }

    public void setReservations(Collection<Reservation> reservations) {
        this.reservations = reservations;
    }


    public String toString() {
        return "User(id=" + this.getId() + ", name=" + this.getName() + ", email=" + this.getEmail() + ", reservations=" + this.getReservations() + ")";
    }
}
