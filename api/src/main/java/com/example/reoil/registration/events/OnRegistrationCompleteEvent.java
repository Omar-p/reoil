package com.example.reoil.registration.events;

import com.example.reoil.domain.security.User;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class OnRegistrationCompleteEvent extends ApplicationEvent {

  private final User user;
  private final Locale locale;
  private final String appUrl;


  public OnRegistrationCompleteEvent(User user, Locale locale, String appUrl) {
    super(user);
    this.user = user;
    this.locale = locale;
    this.appUrl = appUrl;
  }

  public String getAppUrl() {
    return appUrl;
  }

  public User getUser() {
    return user;
  }

  public Locale getLocale() {
    return locale;
  }
}
