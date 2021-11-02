package com.pluralsight.reserve_your_spot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class WorkStation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank
    private String uniqueCode;
    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonIgnoreProperties("stations")
    private OfficeRoom room;

    public WorkStation(String uniqueCode, OfficeRoom room) {
        this.uniqueCode = uniqueCode;
        this.room = room;
    }

    public WorkStation() {
    }

    public WorkStation(int id, String uniqueCode, OfficeRoom room) {
        this.id = id;
        this.uniqueCode = uniqueCode;
        this.room = room;
    }




}
