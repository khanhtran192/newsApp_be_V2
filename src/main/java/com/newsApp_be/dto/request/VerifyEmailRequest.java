package com.newsApp_be.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class VerifyEmailRequest {
    private String email;
    @NotEmpty
    private String code;
}
