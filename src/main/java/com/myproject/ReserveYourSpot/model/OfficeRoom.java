package com.myproject.ReserveYourSpot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Entity
public class OfficeRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank
    @NotNull(message = "OfficeRoom name must have a value!")
    private String name;
    @NotNull(message = "OfficeRoom name must have a order number!")
    private int orderNo;

    public OfficeRoom() {
    }

    public OfficeRoom(String name, int orderNo) {
        this.name = name;
        this.orderNo = orderNo;
    }


}
