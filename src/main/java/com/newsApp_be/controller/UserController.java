package com.newsApp_be.controller;

import com.newsApp_be.dto.request.UserInfoDTO;
import com.newsApp_be.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo() {
        return userService.getUserInfo();
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUserInfo(@RequestBody UserInfoDTO userInfoDTO) {
        return userService.updateUserInfo(userInfoDTO);
    }
}
