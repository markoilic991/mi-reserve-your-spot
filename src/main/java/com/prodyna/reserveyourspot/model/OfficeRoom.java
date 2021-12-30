package com.prodyna.reserveyourspot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicUpdate
@Table(name = "officeRooms")
public class OfficeRoom {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @NotNull(message = "OfficeRoom name must have a value!")
  private String name;

  @NotNull(message = "OfficeRoom name must have a code!")
  @Column(unique = true)
  private int code;

  @ManyToOne
  @JsonIgnore
  @JoinColumn(name = "officeSpace_Id")
  private OfficeSpace officeSpace;

}
