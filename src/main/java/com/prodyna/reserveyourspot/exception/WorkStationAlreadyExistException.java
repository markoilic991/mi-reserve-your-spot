package com.prodyna.reserveyourspot.exception;

public class WorkStationAlreadyExistException extends RuntimeException {

  public WorkStationAlreadyExistException(String message) {
    super(message);
  }
}
