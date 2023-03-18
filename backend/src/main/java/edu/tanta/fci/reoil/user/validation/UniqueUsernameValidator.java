package edu.tanta.fci.reoil.user.validation;

import edu.tanta.fci.reoil.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

	private final UserRepository repository;

	public UniqueUsernameValidator(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		return username != null && repository.findByUsername(username).isEmpty();
	}

}
