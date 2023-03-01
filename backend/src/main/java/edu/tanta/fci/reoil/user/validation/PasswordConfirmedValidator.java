package edu.tanta.fci.reoil.user.validation;


import edu.tanta.fci.reoil.user.model.ChangePassword;
import edu.tanta.fci.reoil.user.model.RegistrationRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordConfirmedValidator implements ConstraintValidator<PasswordConfirmed, Object> {

	Logger logger = LoggerFactory.getLogger(PasswordConfirmedValidator.class);

	@Override
	public boolean isValid(Object request, ConstraintValidatorContext context) {
		if (request instanceof RegistrationRequest r) {
			return r.password().equals(r.passwordConfirmation());
		} else if (request instanceof ChangePassword c) {
			return c.password().equals(c.passwordConfirmation());
		}
		logger.error("[PasswordConfirmedValidator] Invalid request type");
		return false;
	}

}
