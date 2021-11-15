package com.myproject.ReserveYourSpot.exception;

// Comment: general formatting
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
