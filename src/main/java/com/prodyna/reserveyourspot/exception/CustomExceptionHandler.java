package com.prodyna.reserveyourspot.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(OfficeRoomNotFoundException.class)
  public ResponseEntity<ErrorDetails> handleOfficeRoomNotFound(OfficeRoomNotFoundException e, WebRequest request) {

    ErrorDetails errorDetails = new ErrorDetails(
            HttpStatus.NOT_FOUND,
            e.getMessage(),
            ZonedDateTime.now(),
            request.getDescription(true)
    );
    return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<String> handleContraintViolationException(ConstraintViolationException e) {

    return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);

  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    Map<String, Object> body = new HashMap<>();

    List<String> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(x -> x.getDefaultMessage())
            .collect(Collectors.toList());

    body.put("errors", errors);

    return new ResponseEntity<>(body, headers, status);

  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorDetails> EntityNotFoundExceptionHandler(EntityNotFoundException e, ServletWebRequest request) {

    ErrorDetails errorDetails = new ErrorDetails(
            HttpStatus.BAD_REQUEST,
            e.getMessage(),
            ZonedDateTime.now(),
            request.getDescription(true));

    return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ReservationAlreadyExistException.class)
  public ResponseEntity<ErrorDetails> reservationExistExceptionHandler(ReservationAlreadyExistException e, ServletWebRequest request) {

    ErrorDetails errorDetails = new ErrorDetails(
            HttpStatus.BAD_REQUEST,
            e.getMessage(),
            ZonedDateTime.now(),
            request.getDescription(true));

    return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
  }
}


