package edu.tanta.fci.reoil.password;

import edu.tanta.fci.reoil.user.UserService;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class ForgetPasswordListener implements ApplicationListener<ForgetPasswordEvent> {

  private final PasswordService passwordService;

  private final UserService userService;
  private final MailSender mailSender;

  public ForgetPasswordListener(PasswordService passwordService, UserService userService, MailSender mailSender) {
    this.passwordService = passwordService;
    this.userService = userService;
    this.mailSender = mailSender;
  }

  @Override
  public void onApplicationEvent(ForgetPasswordEvent event) {
    String email = event.getEmail();
    if (!userService.isUserExistByEmail(email)) {
      return;
    }
    int code = passwordService.createResetPasswordCode(email);
    SimpleMailMessage message = new SimpleMailMessage();
    message.setSubject("Reset Password");
    message.setText("Reset Password Code: " + code);
    message.setTo(email);
    mailSender.send(message);
  }
}
