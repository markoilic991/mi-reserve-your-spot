package com.prodyna.reserveyourspot.exception;

public class UserAlreadyReservedWorkStationException extends RuntimeException {

  public UserAlreadyReservedWorkStationException(String message) {
    super(message);
  }
}
