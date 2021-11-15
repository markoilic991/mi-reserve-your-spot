package com.myproject.ReserveYourSpot.model;

import lombok.Data;

// Comment: use lombok annotations like @Getter, @Builder, @NoArgsConstructor, @AllArgsConstructor, @ToString, @EqualsAndHashCode instead of @Data
@Data
public class Response {

    private String message;
    // Comment: formatting, please use space between properties
    private boolean status;

}
