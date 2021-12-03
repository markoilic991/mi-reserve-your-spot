package com.prodyna.reserveyourspot.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "reservations")
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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

  public Reservation(LocalDate date, User user, WorkStation workStation) {
    this.date = date;
    this.user = user;
    this.workStation = workStation;
  }

}


