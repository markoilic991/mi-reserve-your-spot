package com.myproject.ReserveYourSpot.exception;

public class NameNotValidException extends RuntimeException{

    public NameNotValidException(String message) {
        super(message);
    }
}
