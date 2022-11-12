package com.newsApp_be.controller;

import com.newsApp_be.dto.request.VerifyEmailRequest;
import com.newsApp_be.security.request.SignupRequest;
import com.newsApp_be.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/rigister")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        return userService.registerUser(signupRequest);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyEmail(@Valid @RequestBody VerifyEmailRequest request) {
        return userService.verifyEmail(request);
    }



}
