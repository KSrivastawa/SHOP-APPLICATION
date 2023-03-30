package com.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(UserException.class)
    public ResponseEntity<MyError> userExceptionHandler(UserException ex, WebRequest req){

        MyError error = new MyError();
        error.setMessage(ex.getMessage());
        error.setStatus(req.getDescription(false));
        error.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MyError> globalExceptionHandler(Exception ex, WebRequest req){

        MyError error = new MyError();
        error.setMessage(ex.getMessage());
        error.setStatus(req.getDescription(false));
        error.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }


}
