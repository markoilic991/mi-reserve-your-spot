package com.myproject.ReserveYourSpot.exception;

// Comment: general formatting
public class UserAlreadyReservedWorkStationException extends RuntimeException{

    public UserAlreadyReservedWorkStationException(String message) {
        super(message);
    }
}
