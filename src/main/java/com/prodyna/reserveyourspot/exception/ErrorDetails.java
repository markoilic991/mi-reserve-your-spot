package com.prodyna.reserveyourspot.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

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
