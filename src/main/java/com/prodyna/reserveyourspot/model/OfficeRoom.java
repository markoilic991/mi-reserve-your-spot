package com.prodyna.reserveyourspot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicUpdate
@Table(name = "officeRooms")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OfficeRoom {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @NotBlank(message = "OfficeRoom name must have a value!")
  private String name;

  @NotBlank(message = "OfficeRoom code must have a value!")
  @Column(unique = true)
  private int code;

  @ManyToOne
  @JsonIgnore
  @JoinColumn(name = "officeSpace_Id")
  private OfficeSpace officeSpace;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "officeRoom", orphanRemoval = true, fetch = FetchType.LAZY)
  private Set<WorkStation> workStations = new HashSet<>();
}
