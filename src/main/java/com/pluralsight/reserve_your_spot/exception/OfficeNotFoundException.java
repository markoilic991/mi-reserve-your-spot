package com.pluralsight.reserve_your_spot.exception;

public class OfficeNotFoundException extends RuntimeException {

    public OfficeNotFoundException(String message) {
        super(message);
    }
}
