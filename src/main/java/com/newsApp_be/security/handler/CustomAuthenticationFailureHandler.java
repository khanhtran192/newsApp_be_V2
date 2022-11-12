package com.newsApp_be.security.handler;

import com.newsApp_be.security.response.MessageResponse;
import com.newsApp_be.security.response.ResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        MessageResponse<Object> messageResponse = MessageResponse.builder()
                .message(exception.getMessage())
                .status(ResponseStatus.FAIL.getValue())
                .build();
        response.setContentType("text/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(objectMapper.writeValueAsString(messageResponse));
    }
}
