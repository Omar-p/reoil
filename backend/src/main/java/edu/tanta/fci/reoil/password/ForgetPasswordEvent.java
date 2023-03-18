package edu.tanta.fci.reoil.password;

import org.springframework.context.ApplicationEvent;

public class ForgetPasswordEvent extends ApplicationEvent {

  public ForgetPasswordEvent(Object email) {
    super(email);
 }

  public String getEmail() {
    return (String)super.getSource();
  }
}
