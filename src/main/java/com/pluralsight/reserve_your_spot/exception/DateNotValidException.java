package com.pluralsight.reserve_your_spot.exception;

public class DateNotValidException extends RuntimeException {

    public DateNotValidException(String message) {
        super(message);
    }
}
