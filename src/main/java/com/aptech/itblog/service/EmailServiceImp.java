package com.aptech.itblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service("emailService")
public class EmailServiceImp implements EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendEmail(SimpleMailMessage email) {
        mailSender.send(email);
    }

    @Async
    public void sendEmail(String subject, String htmlContent, String sendTo) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
        helper.setTo(sendTo);
        helper.setFrom("support@itblog.com");
        helper.setSubject(subject);
        mimeMessage.setContent(htmlContent, "text/html; charset=utf-8");
        mailSender.send(mimeMessage);
    }
}
