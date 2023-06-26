package com.laptech.restapi.service.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    @Override
    public void sendPasswordResetEmail(HttpServletRequest request, String passwordResetUrl, String token, String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@laptech.com");
        message.setTo(toEmail);
        message.setSubject("RESET PASSWORD LINK");
        message.setText("Your reset password token is\n"
                + token
                + "\nUse this to set new password");
        mailSender.send(message);
    }
}
