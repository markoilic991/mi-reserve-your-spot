package com.pluralsight.reserve_your_spot.exception;

public class UserAlreadyReservedWorkStationException extends RuntimeException{

    public UserAlreadyReservedWorkStationException(String message) {
        super(message);
    }
}
