package com.pluralsight.reserve_your_spot.exception;

public class NameNotValidException extends RuntimeException{

    public NameNotValidException(String message) {
        super(message);
    }
}
