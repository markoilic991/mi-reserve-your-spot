package com.prodyna.reserveyourspot.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorDetails> handleConstraintViolationException(ConstraintViolationException exception, ServletWebRequest request) {

    ErrorDetails errorDetails = new ErrorDetails(
            HttpStatus.BAD_REQUEST,
            exception.getMessage(),
            ZonedDateTime.now(),
            request.getDescription(true));

    return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {

    ErrorDetails errorDetails = new ErrorDetails(
            HttpStatus.BAD_REQUEST,
            exception.getMessage(),
            ZonedDateTime.now(),
            request.getDescription(true));

    return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorDetails> EntityNotFoundExceptionHandler(EntityNotFoundException exception, ServletWebRequest request) {

    ErrorDetails errorDetails = new ErrorDetails(
            HttpStatus.NOT_FOUND,
            exception.getMessage(),
            ZonedDateTime.now(),
            request.getDescription(true));

    return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UniqueValueException.class)
  public ResponseEntity<ErrorDetails> UniqueValueExceptionHandler(UniqueValueException exception, ServletWebRequest request) {

    ErrorDetails errorDetails = new ErrorDetails(
            HttpStatus.BAD_REQUEST,
            exception.getMessage(),
            ZonedDateTime.now(),
            request.getDescription(true));

    return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
  }
}


