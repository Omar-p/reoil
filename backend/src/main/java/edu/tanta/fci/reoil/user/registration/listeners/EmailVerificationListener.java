package edu.tanta.fci.reoil.user.registration.listeners;

import edu.tanta.fci.reoil.user.registration.VerificationService;
import edu.tanta.fci.reoil.user.registration.events.UserRegistrationEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationListener implements ApplicationListener<UserRegistrationEvent> {

  private final JavaMailSender mailSender;
  private final VerificationService verificationService;



  @Value(value = "${disable.email.verification}")
  private boolean disableEmailVerification;

  @Value(value = "${app.url:https://localhost:8080}")
  private String url;
  public EmailVerificationListener(JavaMailSender mailSender, VerificationService verificationService) {
    this.mailSender = mailSender;
    this.verificationService = verificationService;
  }

  @Override
  public void onApplicationEvent(UserRegistrationEvent event) {
    if(disableEmailVerification) {
      return;
    }
    String username = event.getUser().getUsername();
    String verificationId = verificationService.createVerification(username);
    String email = event.getUser().getEmail();
    SimpleMailMessage message = new SimpleMailMessage();
    message.setSubject("Reoil Account Verification");
    message.setText("Account activation link: \r\n"+ url + "/api/v1/registration?token=" +  verificationId);
    message.setTo(email);
    mailSender.send(message);
  }
}
