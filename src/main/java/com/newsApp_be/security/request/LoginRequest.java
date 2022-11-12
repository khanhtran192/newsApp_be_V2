package com.newsApp_be.security.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
