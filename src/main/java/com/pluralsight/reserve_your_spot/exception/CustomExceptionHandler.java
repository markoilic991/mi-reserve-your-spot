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
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

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


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetails handleMethodArgNotValid(MethodArgumentNotValidException exception, HttpServletRequest request){

        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                ZonedDateTime.now(),
                request.getServletPath()
        );
        BindingResult bindingResult = exception.getBindingResult();
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError fieldError: bindingResult.getFieldErrors()
             ) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        errorDetails.setValidationErrors(validationErrors);
        return errorDetails;
    }


    //making custom exception for our controller OfficeRoom
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleContraintViolationException(ConstraintViolationException e){

        return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);

    }

}


