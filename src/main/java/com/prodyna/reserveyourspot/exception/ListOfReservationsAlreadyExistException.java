package com.prodyna.reserveyourspot.exception;

public class ListOfReservationsAlreadyExistException extends RuntimeException {

  public ListOfReservationsAlreadyExistException(String message) {
    super(message);
  }
}
