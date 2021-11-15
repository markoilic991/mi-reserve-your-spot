package com.myproject.ReserveYourSpot.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import java.time.ZonedDateTime;

// Comment: general formatting
// Comment: use lombok annotations like @Getter, @Builder, @NoArgsConstructor, @AllArgsConstructor, @ToString, @EqualsAndHashCode instead of @Data
@Data
public class ErrorDetails {

    private HttpStatus httpStatus;
    private String message;
    private ZonedDateTime timeStamp;
    private String path;

    // Comment: use lombok
    public ErrorDetails(HttpStatus httpStatus, String message, ZonedDateTime timeStamp, String path) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.timeStamp = timeStamp;
        this.path = path;
    }
}
