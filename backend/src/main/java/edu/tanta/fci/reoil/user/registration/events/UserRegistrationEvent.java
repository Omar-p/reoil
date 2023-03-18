package edu.tanta.fci.reoil.user.registration.events;

import edu.tanta.fci.reoil.user.entities.User;
import org.springframework.context.ApplicationEvent;

public class UserRegistrationEvent extends ApplicationEvent {

  private final User user;

  public UserRegistrationEvent(User user) {
    super(user);
    this.user = user;
  }

  public User getUser() {
    return user;
  }
}
