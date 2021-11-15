package com.myproject.ReserveYourSpot.exception;

// Comment: general formatting
public class ReservationAlreadyExistException extends RuntimeException{

    public ReservationAlreadyExistException(String message) {
        super(message);
    }
}
