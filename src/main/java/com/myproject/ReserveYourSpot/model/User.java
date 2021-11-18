package com.myproject.ReserveYourSpot.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// Comment: general formatting
// Comment: .*; should not be used, import only what we need
// Comment: use lombok annotations like @Getter, @Builder, @NoArgsConstructor, @AllArgsConstructor, @ToString, @EqualsAndHashCode
@Entity
@Table(name = "users")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SerializedName("id")
    @Expose
    private int id;
    // Comment: formatting, please use space between properties
    @NotBlank
    // Comment: NotBlank annotation covers NotNull also so NotNull is obsolete
    @NotNull(message = "User name must have a value!")
    @SerializedName("name")
    @Expose
    private String name;
    @NotNull(message = "Email must have a value!")
    // Comment: NotBlank should be used here?
    @Email
    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    // Comment: why using collection interface instead of List interface?
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

    // Comment: use lombok
    public User() {
    }

    // Comment: use lombok
    // Comment: getters and setters go in pair like getX, detX, getY, setY
    // Comment: no need for NotBlank/Email annotation on getter if the same is present on a property
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

    // Advice: good practice is to omit id (PK) from toString method
    public String toString() {
        return "User(id=" + this.getId() + ", name=" + this.getName() + ", email=" + this.getEmail() + ", reservations=" + this.getReservations() + ")";
    }
}
