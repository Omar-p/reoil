package edu.tanta.fci.reoil.worker;

import org.springframework.context.ApplicationEvent;

import java.util.Map;

public class AssignedOrderAcceptedEvent extends ApplicationEvent {
  public AssignedOrderAcceptedEvent(Map<String, Object> orderAndUser) {
    super(orderAndUser);
  }
}
