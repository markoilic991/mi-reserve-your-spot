package com.myproject.ReserveYourSpot.exception;

public class OfficeNotFoundException extends RuntimeException {

    public OfficeNotFoundException(String message) {
        super(message);
    }
}
