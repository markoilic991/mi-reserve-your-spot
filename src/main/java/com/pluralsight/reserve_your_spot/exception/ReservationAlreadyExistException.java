package com.pluralsight.reserve_your_spot.exception;

public class ReservationAlreadyExistException extends RuntimeException{

    public ReservationAlreadyExistException(String message) {
        super(message);
    }
}
