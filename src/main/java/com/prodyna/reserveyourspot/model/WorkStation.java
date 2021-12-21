package com.prodyna.reserveyourspot.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Validated
@Table(name = "workStations")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WorkStation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @NotNull(message = "Every workStation must have it's unique code!")
  private String uniqueCode;

  @NotNull
  private String description;

  @ManyToOne(cascade = CascadeType.MERGE)
  @JsonIgnoreProperties("workStations")
  @JoinColumn(name = "officeRoom_id")
  private OfficeRoom officeRoom;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "workStation", orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Reservation> reservations = new ArrayList<>();

  public WorkStation(int id) {
    this.id = id;
  }

  public WorkStation(int id, String uniqueCode, String description) {
    this.id = id;
    this.uniqueCode = uniqueCode;
    this.description = description;
  }
}
