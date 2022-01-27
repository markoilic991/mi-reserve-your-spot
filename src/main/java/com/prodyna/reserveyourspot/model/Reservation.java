package com.prodyna.reserveyourspot.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicUpdate
@Table(name = "reservations")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @FutureOrPresent(message = "The arrival date must be today or in the future.")
  @NotNull
  private LocalDate date;

  @ManyToOne
  @JsonIgnore
  @JoinColumn(name = "workStation_id")
  private WorkStation workStation;

  @ManyToOne
  @JsonIgnore
  @JoinColumn(name = "user_id")
  private User user;

  public Reservation(LocalDate date, User user, WorkStation workStation) {
    this.date = date;
    this.user = user;
    this.workStation = workStation;
  }
}
