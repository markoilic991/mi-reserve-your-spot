package com.pluralsight.reserve_your_spot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
public class OfficeRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank
    private String name;
    @NotNull
    private int orderNo;
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "room"
    )
    @JsonIgnoreProperties("room")
    private Collection<WorkStation> stations = new ArrayList<>();



    public OfficeRoom() {
    }

    public OfficeRoom(String name, int orderNo) {
        this.name = name;
        this.orderNo = orderNo;
    }

    public OfficeRoom(int id, String name, int orderNo, Collection<WorkStation> stations) {
        this.id = id;
        this.name = name;
        this.orderNo = orderNo;
        this.stations = stations;
    }

    public OfficeRoom(int id, String name, int orderNo) {
        this.id = id;
        this.name = name;
        this.orderNo = orderNo;
    }
}
