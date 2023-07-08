package edu.tanta.fci.reoil.user.validation;


import edu.tanta.fci.reoil.password.model.ChangePassword;
import edu.tanta.fci.reoil.password.model.ResetPassword;
import edu.tanta.fci.reoil.user.model.RegistrationRequest;
import edu.tanta.fci.reoil.worker.requests.WorkerRegistrationRequest;
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
		} else if (request instanceof ResetPassword r) {
			return r.password().equals(r.passwordConfirmation());
		} else if (request instanceof WorkerRegistrationRequest r) {
			return r.password().equals(r.passwordConfirmation());
		}

		return false;
	}

}
