package edu.tanta.fci.reoil.user.validation;

import edu.tanta.fci.reoil.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

	private final UserRepository repository;

	public UniqueEmailValidator(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		return email != null && repository.findByEmail(email).isEmpty() ;
	}

}
