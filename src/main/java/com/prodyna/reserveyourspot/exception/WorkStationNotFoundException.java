package com.prodyna.reserveyourspot.exception;

public class WorkStationNotFoundException extends RuntimeException{
  public WorkStationNotFoundException(String message) {
    super(message);
  }
}
