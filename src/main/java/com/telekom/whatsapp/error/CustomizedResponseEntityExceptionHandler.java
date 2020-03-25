package com.telekom.whatsapp.error;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.TransientPropertyValueException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    protected ResponseEntity<ExceptionResponse> handleNotFoundException (NotFoundException err, WebRequest request) {
        List<String> messages = new LinkedList<>();
        messages.add(err.getMessage());

        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), messages, 
        request.getDescription(false), HttpStatus.NOT_FOUND.getReasonPhrase());

        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException err, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult result = err.getBindingResult();
        List<org.springframework.validation.FieldError> fieldErrors = result.getFieldErrors();
        List<String> messages = new LinkedList<>();
        fieldErrors.forEach((ferr) -> messages.add(ferr.getDefaultMessage()));
        
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), messages, 
        request.getDescription(false), HttpStatus.BAD_REQUEST.getReasonPhrase());

        return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TransientPropertyValueException.class})
    protected ResponseEntity<Object> handleTransientPropertyValueException(TransientPropertyValueException err, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> messages = new LinkedList<>();
        messages.add(err.getMessage());

        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), messages, 
        request.getDescription(false), HttpStatus.BAD_REQUEST.getReasonPhrase());

        return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HttpMessageConversionException.class})
    protected ResponseEntity<Object> handleHttpMessageConversionException(HttpMessageConversionException e) {
        List<String> messages = new LinkedList<>();
        messages.add(e.getMessage());

        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), messages, HttpStatus.BAD_REQUEST.getReasonPhrase());

        return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}