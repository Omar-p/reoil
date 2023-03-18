package edu.tanta.fci.reoil.user.registration;

import edu.tanta.fci.reoil.user.UserService;
import edu.tanta.fci.reoil.user.entities.User;
import edu.tanta.fci.reoil.user.model.RegistrationRequest;
import edu.tanta.fci.reoil.user.registration.events.UserRegistrationEvent;
import edu.tanta.fci.reoil.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegistrationService {

  private final VerificationService verificationService;

  private final UserService userService;

  private final ApplicationEventPublisher eventPublisher;

  private final PasswordEncoder encoder;

  @Value(value = "${disable.email.verification}")
  private boolean disableEmailVerification;

  public RegistrationService(VerificationService verificationService, UserService userService, ApplicationEventPublisher eventPublisher, PasswordEncoder encoder) {
    this.verificationService = verificationService;
    this.userService = userService;
    this.eventPublisher = eventPublisher;
    this.encoder = encoder;
  }

  public void processUserRegistration(RegistrationRequest request) {
    final User user = toUser(request);
    var newUser = userService.createUser(user);
    verificationService.createVerification(newUser.getUsername());
    eventPublisher.publishEvent(new UserRegistrationEvent(newUser));
  }


  public void verifyUser(String token) {
    var username = verificationService.getUsernameForId(token);
    verificationService.deleteVerification(token);
    userService.enableUser(username);

  }



  private User toUser(RegistrationRequest request) {
    return new User(
        request.username(),
        request.email(),
        encoder.encode(request.password()),
        request.phoneNumber(),
        disableEmailVerification
    );
  }
}
