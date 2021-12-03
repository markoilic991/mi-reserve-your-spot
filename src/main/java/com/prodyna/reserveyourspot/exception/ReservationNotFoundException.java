package com.prodyna.reserveyourspot.exception;

public class ReservationNotFoundException extends RuntimeException{
  public ReservationNotFoundException(String message) {
    super(message);
  }
}
