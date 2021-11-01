package com.pluralsight.reserve_your_spot.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZonedDateTime;
import java.util.Arrays;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NameNotValidException.class)
    public ResponseEntity<?> nameNotValidExceptionHandler(NameNotValidException e, ServletWebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                ZonedDateTime.now(),
                request.getDescription(true));

        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFound(UserNotFoundException e, WebRequest request){

        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.NOT_FOUND,
                e.getMessage(),
                ZonedDateTime.now(),
                request.getDescription(true)
        );
        return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers, HttpStatus status,
                                                                   WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                ZonedDateTime.now(),
                request.getDescription(true)
        );
        return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OfficeNotFoundException.class)
    public ResponseEntity<Object> handleOfficeNotFound(OfficeNotFoundException e, WebRequest request){

        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.NOT_FOUND,
                e.getMessage(),
                ZonedDateTime.now(),
                request.getDescription(true)
        );
        return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
    }
}
