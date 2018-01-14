package com.aptech.itblog.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public interface EmailService {
    void sendEmail(SimpleMailMessage email);
    void sendEmail(String subject, String htmlContent, String sendTo) throws MessagingException;
}
