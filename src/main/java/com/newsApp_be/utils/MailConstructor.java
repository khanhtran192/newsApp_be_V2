package com.newsApp_be.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;


@Component
public class MailConstructor {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOTPEmail(String email, String randomCode) throws MessagingException, UnsupportedEncodingException {
        String fromAddress = "khanhtd192@gmail.com";
        String senderName = "Báo Nhà";
        String subject = "Please verify your email";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(email);
        helper.setSubject(subject);
        String content = "Mã xác thực của bạn là: " + String.valueOf(randomCode);
        helper.setText(content, true);
        mailSender.send(message);
    }
}
