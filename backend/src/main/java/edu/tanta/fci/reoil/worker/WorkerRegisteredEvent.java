package edu.tanta.fci.reoil.worker;

import edu.tanta.fci.reoil.domain.Worker;
import org.springframework.context.ApplicationEvent;

public class WorkerRegisteredEvent extends ApplicationEvent {
  public WorkerRegisteredEvent(Worker worker) {
    super(worker);
  }
}
