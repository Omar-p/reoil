package com.example.reoil.registration;

import com.example.reoil.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class RegistrationMailService implements MailService {

  @Value("${spring.mail.username}")
  private String from;
  private final JavaMailSender mailSender;

  public RegistrationMailService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  @Override
  public void sendSimpleMail(String to, String subject, String content, String... cc) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(from);
    message.setTo(to);
    message.setSubject(subject);
    message.setText(content);
    if (ArrayUtils.isNotEmpty(cc)) {
      message.setCc(cc);
    }
    mailSender.send(message);
  }

  @Override
  public void sendHtmlMail(String to, String subject, String content, String... cc) throws MessagingException {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);
    helper.setFrom(from);
    helper.setTo(to);
    helper.setSubject(subject);
    helper.setText(content, true);
    if (ArrayUtils.isNotEmpty(cc)) {
      helper.setCc(cc);
    }
    mailSender.send(message);
  }

}
