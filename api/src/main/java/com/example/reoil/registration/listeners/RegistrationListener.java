package com.example.reoil.registration.listeners;

import com.example.reoil.registration.events.OnRegistrationCompleteEvent;
import com.example.reoil.service.MailService;
import com.example.reoil.user.User;
import com.example.reoil.user.UserService;
import jakarta.mail.MessagingException;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

  private final UserService userService;
  private final MessageSource messageSource;

  private final MailService mailService;

  public RegistrationListener(UserService userService, MessageSource messageSource, MailService mailService) {
    this.userService = userService;
    this.messageSource = messageSource;
    this.mailService = mailService;
  }

  @Override
  public void onApplicationEvent(OnRegistrationCompleteEvent event) {
    try {
      this.confirmRegistration(event);
    } catch (MessagingException e) {
      System.out.println("ERROR");
      throw new RuntimeException(e);
    }
  }

  private void confirmRegistration(OnRegistrationCompleteEvent event) throws MessagingException {
    User user = event.getUser();
    String token = UUID.randomUUID().toString();
    userService.createVerificationToken(user, token);

    String recipientAddress = user.getEmail();
    String subject = "Registration Confirmation";
    String confirmationUrl
        = event.getAppUrl() + "/api/registrationConfirm?token=" + token;
    String message = messageSource.getMessage("registration.email", null, event.getLocale());

    String text = String.format("%s <br><br> http://localhost:8080%s", message, confirmationUrl);
    mailService.sendHtmlMail(recipientAddress,
        subject,
        """
        <h3> Registration Confirmation </h3>
        
        """ + text);
  }
}
