package com.example.webshop.controllers;

import com.example.webshop.exceptions.CodeNotUniqueException;
import com.example.webshop.exceptions.ResourceNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    public RestResponseEntityExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception exception, WebRequest request){
        return  new ResponseEntity<Object>("Resource not Found", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({CodeNotUniqueException.class})
    public ResponseEntity<Object> handleCodeNotUniqueException(Exception exception, WebRequest request){
        return new ResponseEntity<Object>("Code not unique", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
