package com.newsApp_be.services;

import com.newsApp_be.dto.request.VerifyEmailRequest;
import com.newsApp_be.dto.request.UserInfoDTO;
import com.newsApp_be.security.request.LoginRequest;
import com.newsApp_be.security.request.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> authenticateUser(LoginRequest loginRequest);
    ResponseEntity<?> registerUser(SignupRequest signupRequest);
    ResponseEntity<?> verifyEmail(VerifyEmailRequest verifyEmailRequest);

    ResponseEntity<?> getUserInfo();

    ResponseEntity<?> updateUserInfo(UserInfoDTO request);
}
