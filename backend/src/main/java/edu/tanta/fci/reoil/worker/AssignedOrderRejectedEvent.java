package edu.tanta.fci.reoil.worker;

import org.springframework.context.ApplicationEvent;

import java.util.Map;

public class AssignedOrderRejectedEvent extends ApplicationEvent {
  public AssignedOrderRejectedEvent(Map<String, Object> orderAndUser) {
    super(orderAndUser);
  }
}
