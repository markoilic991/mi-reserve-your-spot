package com.pluralsight.reserve_your_spot.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(NameNotValidException.class)
    public ResponseEntity<?> nameNotValidExceptionHandler(NameNotValidException e, ServletWebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                ZonedDateTime.now(),
                request.getDescription(true));

        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateNotValidException.class)
    public ResponseEntity<?> nameNotValidExceptionHandler(DateNotValidException e, ServletWebRequest request) {

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


    //making custom exception for our controller OfficeRoom
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleContraintViolationException(ConstraintViolationException e){

        return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, Object> body = new HashMap<>();

        List<String>errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);

    }
}


