package com.myproject.ReserveYourSpot.exception;

public class WorkStationBusyException extends RuntimeException{

    public WorkStationBusyException(String message) {
        super(message);
    }
}
