package com.shop.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MyError {

    private String message;
    private String description;
    private LocalDateTime dateTime;

}
