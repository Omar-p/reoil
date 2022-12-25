package com.example.reoil.service;

import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public interface MailService {

  void sendSimpleMail(String to, String subject, String content, String... cc);

  void sendHtmlMail(String to, String subject, String content, String... cc) throws MessagingException;
}
