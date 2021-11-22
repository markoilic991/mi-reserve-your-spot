package com.prodyna.reserveyourspot.model;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
@Table(name = "officeRooms")
public class OfficeRoom {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @NotNull(message = "OfficeRoom name must have a value!")
  private String name;

  @NotNull(message = "OfficeRoom name must have a order number!")
  @Min(1)
  @Max(4)
  private int orderNo;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "officeRoom", orphanRemoval = true)
  private List<WorkStation> workStations = new ArrayList<>();

  @ManyToOne(cascade = CascadeType.MERGE)
  @JsonIgnoreProperties("rooms")
  @JoinColumn(name = "officeSpace_Id")
  private OfficeSpace officeSpace;

  public OfficeRoom(String name, int orderNo) {
    this.name = name;
    this.orderNo = orderNo;
  }

  public OfficeRoom(int id, String name, int orderNo) {
    this.id = id;
    this.name = name;
    this.orderNo = orderNo;
  }

}
