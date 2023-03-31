package com.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ShopException.class)
    public ResponseEntity<MyError> handleShopException(ShopException e, WebRequest request) {
        MyError error = new MyError();
        error.setMessage(e.getMessage());
        error.setDescription(request.getDescription(false));
        error.setDateTime(LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MyError> handleException(Exception e, WebRequest request) {
        MyError error = new MyError();
        error.setMessage(e.getMessage());
        error.setDescription(request.getDescription(false));
        error.setDateTime(LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
