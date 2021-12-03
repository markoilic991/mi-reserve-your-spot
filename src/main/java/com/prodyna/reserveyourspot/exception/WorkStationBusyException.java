package com.prodyna.reserveyourspot.exception;

public class WorkStationBusyException extends RuntimeException {
  public WorkStationBusyException(String message) {
    super(message);
  }
}
