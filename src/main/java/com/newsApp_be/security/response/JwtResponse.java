package com.newsApp_be.security.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private Object expiration;
    private String username;
    private String fullname;
    private String token;
    private Object roles;
}
