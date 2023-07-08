package edu.tanta.fci.reoil.worker.validation;

import edu.tanta.fci.reoil.worker.WorkerRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueWorkerEmailValidator implements ConstraintValidator<UniqueWorkerEmail, String> {
  private final WorkerRepository repository;

  public UniqueWorkerEmailValidator(WorkerRepository repository) {
    this.repository = repository;
  }

  @Override
  public boolean isValid(String email, ConstraintValidatorContext context) {
    return email != null && repository.findByEmail(email).isEmpty() ;
  }
}
