package com.newsApp_be.security.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageResponse<T> {
    private String message;
    private Integer status;
    private T data;

    public MessageResponse(String message, Integer status) {
        this.message = message;
        this.status = status;
    }

    public MessageResponse(String message, Integer status, T data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public MessageResponse(Integer status, T data) {
        this.status = status;
        this.data = data;
    }
}
