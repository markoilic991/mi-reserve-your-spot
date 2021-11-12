package com.pluralsight.reserve_your_spot.exception;

public class StationAlreadyExistException extends RuntimeException{

    public StationAlreadyExistException(String message) {
        super(message);
    }
}
