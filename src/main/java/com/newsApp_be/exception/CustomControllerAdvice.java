package com.newsApp_be.exception;

import com.newsApp_be.security.response.MessageResponse;
import com.newsApp_be.security.response.ResponseStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<?> handleException(BadRequestException exception) {
        return ResponseEntity.badRequest()
                .body(new MessageResponse<>(exception.getMessage(),
                        ResponseStatus.FAIL.getValue()));
    }
}
