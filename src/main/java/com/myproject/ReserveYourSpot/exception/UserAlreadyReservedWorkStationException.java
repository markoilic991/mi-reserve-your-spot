package com.myproject.ReserveYourSpot.exception;

public class UserAlreadyReservedWorkStationException extends RuntimeException{

    public UserAlreadyReservedWorkStationException(String message) {
        super(message);
    }
}
