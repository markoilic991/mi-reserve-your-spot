package com.pluralsight.reserve_your_spot.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@Data
public class ErrorDetails {

    private HttpStatus httpStatus;
    private String message;
    private ZonedDateTime timeStamp;
    private String path;


    public ErrorDetails(HttpStatus httpStatus, String message, ZonedDateTime timeStamp, String path) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.timeStamp = timeStamp;
        this.path = path;
    }
}
