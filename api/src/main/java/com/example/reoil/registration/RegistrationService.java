package com.example.reoil.registration;

import com.example.reoil.GenericResponse;
import com.example.reoil.domain.VerificationToken;
import com.example.reoil.registration.dtos.UserRegistration;
import com.example.reoil.registration.events.OnRegistrationCompleteEvent;
import com.example.reoil.registration.exceptions.ExpiredRegistrationTokenException;
import com.example.reoil.registration.exceptions.InvalidRegistrationTokenException;
import com.example.reoil.domain.security.User;
import com.example.reoil.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class RegistrationService {

  private final UserService userService;

  private final ApplicationEventPublisher eventPublisher;

  private final PasswordEncoder encoder;

  private final MessageSource messageSource;
  public RegistrationService(UserService userService, ApplicationEventPublisher eventPublisher, PasswordEncoder encoder, MessageSource messageSource) {
    this.userService = userService;
    this.eventPublisher = eventPublisher;
    this.encoder = encoder;
    this.messageSource = messageSource;
  }

  public GenericResponse registerUser(UserRegistration userRegistration, HttpServletRequest request) {
    User registered = userService.registerNewUserAccount(toUser(userRegistration, encoder));

    String appUrl = request.getContextPath();
    eventPublisher.publishEvent(
        new OnRegistrationCompleteEvent(
          registered,
          request.getLocale(),
          appUrl
        )
    );
    var message = messageSource.getMessage("registration.success", null, request.getLocale());
    return new GenericResponse(message);

  }

  public GenericResponse confirmRegistration(Locale locale, String token) {
    var verificationToken = userService.getVerificationToken(token);
    throwsExceptionIfTheTokenIsNotExist(locale, verificationToken);

    User user = verificationToken.get().getUser();
    throwsExceptionIfTheTokenIsExpired(locale, verificationToken);

    var message = activateUser(locale, verificationToken, user);
    return new GenericResponse(message);

  }

  private String activateUser(Locale locale, Optional<VerificationToken> verificationToken, User user) {
    user.setEnabled(true);
    verificationToken.get().setActivationTime(LocalDateTime.now());
    userService.saveRegisteredUser(user);
    return messageSource.getMessage("auth.message.userAccountActivated", null, locale);
  }


  private void throwsExceptionIfTheTokenIsExpired(Locale locale, Optional<VerificationToken> verificationToken) {
    Calendar cal = Calendar.getInstance();
    if ((verificationToken.get().getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
      String message = messageSource.getMessage("auth.message.expired", null, locale);
      throw new ExpiredRegistrationTokenException(message);
    }
  }

  private void throwsExceptionIfTheTokenIsNotExist(Locale locale, Optional<VerificationToken> verificationToken) {
    if (verificationToken.isEmpty()) {
      String message = messageSource.getMessage("auth.message.invalidToken", null, locale);
      throw new InvalidRegistrationTokenException(message);

    }
  }


  private User toUser(UserRegistration userRegistration, PasswordEncoder encoder) {
    return new User(
        userRegistration.username(),
        userRegistration.email(),
        encoder.encode(userRegistration.password()),
        userRegistration.phone()
    );
  }



}
