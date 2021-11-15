package com.myproject.ReserveYourSpot.exception;

// Comment: general formatting
public class StationAlreadyExistException extends RuntimeException{

    public StationAlreadyExistException(String message) {
        super(message);
    }
}
