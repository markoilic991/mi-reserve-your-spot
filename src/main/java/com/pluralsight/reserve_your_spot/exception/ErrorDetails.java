package com.pluralsight.reserve_your_spot.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ErrorDetails {

    private HttpStatus httpStatus;
    private String message;
    private ZonedDateTime timeStamp;
    private String path;


}
