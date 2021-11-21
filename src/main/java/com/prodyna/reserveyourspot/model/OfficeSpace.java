package com.prodyna.reserveyourspot.model;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "officeSpace")
public class OfficeSpace {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String description;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "officeSpace", orphanRemoval = true)
  private List<OfficeRoom> rooms = new ArrayList<>();

  public OfficeSpace(int id, String description) {
    this.id = id;
    this.description = description;
  }
}
