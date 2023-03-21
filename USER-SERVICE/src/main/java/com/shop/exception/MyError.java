package com.shop.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MyError {

    private String message;
    private String status;
    private LocalDateTime timestamp;

}
