package com.pluralsight.reserve_your_spot.exception;

public class WorkStationBusyException extends RuntimeException{

    public WorkStationBusyException(String message) {
        super(message);
    }
}
