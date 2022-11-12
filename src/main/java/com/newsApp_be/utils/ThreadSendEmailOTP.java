package com.newsApp_be.utils;

import com.newsApp_be.exception.BadRequestException;
import com.newsApp_be.entity.User;
import com.newsApp_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class ThreadSendEmailOTP extends Thread {

    private String email;
    private String verifyCode;


    private final MailConstructor mailConstructor;

    private final UserRepository userRepository;

    @Override
    public void run() {
        try {
            mailConstructor.sendOTPEmail(this.email, this.verifyCode);
            Thread.sleep(300000);
            User user = userRepository.findByEmail(email).orElseThrow(() -> new BadRequestException("Email không tồm tại"));
            user.setVerifyCode(null);
            userRepository.save(user);
        } catch (MessagingException | InterruptedException | UnsupportedEncodingException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public void start(String email, String verifyCode) {
        this.email = email;
        this.verifyCode = verifyCode;
        Thread thread = new Thread(this);
        thread.start();
    }


}
