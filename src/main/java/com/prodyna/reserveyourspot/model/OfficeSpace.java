package com.prodyna.reserveyourspot.model;

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
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedEntityGraph(
        name = "graph.officeSpace.OfficeRoom.WorkStations.Reservations",
        attributeNodes = @NamedAttributeNode(value = "rooms", subgraph = "subgraph.OfficeRoom"),
        subgraphs = {
                @NamedSubgraph(name = "subgraph.OfficeRoom",
                        attributeNodes = @NamedAttributeNode(value = "workStations", subgraph = "subgraph.WorkStation")),
                @NamedSubgraph(name = "subgraph.WorkStation",
                        attributeNodes = @NamedAttributeNode(value = "reservations"))})
@DynamicUpdate
@Table(name = "officeSpaces")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OfficeSpace {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @NotNull(message = "OfficeSpace name must have a value!")
  @Column(unique = true)
  private String name;

  @NotNull
  private String description;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "officeSpace", orphanRemoval = true, fetch = FetchType.LAZY)
  private Set<OfficeRoom> rooms = new HashSet<>();
}
