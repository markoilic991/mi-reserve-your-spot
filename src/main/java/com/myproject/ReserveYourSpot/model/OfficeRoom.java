package com.myproject.ReserveYourSpot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

// Comment: general formatting
// Comment: .*; should not be used, import only what we need
// Comment: missing entity Office space and missing FK towards it
// Comment: use lombok annotations like @Getter, @Builder, @NoArgsConstructor, @ToString, @EqualsAndHashCode instead of @Data
@Data
@AllArgsConstructor
@Entity
public class OfficeRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    // Comment: formatting, please use space between properties

    // Comment: NotBlank annotation covers NotNull also so NotNull is obsolete
    @NotBlank
    @NotNull(message = "OfficeRoom name must have a value!")
    private String name;
    @NotNull(message = "OfficeRoom name must have a order number!")
    private int orderNo;

    // Comment: in lombok there is NoArgsConstructor anotation
    public OfficeRoom() {
    }

    public OfficeRoom(String name, int orderNo) {
        this.name = name;
        this.orderNo = orderNo;
    }


}
