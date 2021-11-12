package com.myproject.ReserveYourSpot.exception;

public class ReservationAlreadyExistException extends RuntimeException{

    public ReservationAlreadyExistException(String message) {
        super(message);
    }
}
