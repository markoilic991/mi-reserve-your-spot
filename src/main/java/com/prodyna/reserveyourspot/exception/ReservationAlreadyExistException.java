package com.prodyna.reserveyourspot.exception;

public class ReservationAlreadyExistException extends RuntimeException {

  public ReservationAlreadyExistException(String message) {
    super(message);
  }
}
