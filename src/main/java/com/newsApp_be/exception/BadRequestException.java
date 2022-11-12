package com.newsApp_be.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadRequestException extends RuntimeException {
    private String message;
    private HttpStatus status;


    public BadRequestException(String message) {
        super(message);
        this.message = message;
    }

}
