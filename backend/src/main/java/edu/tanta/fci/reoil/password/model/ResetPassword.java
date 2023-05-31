package edu.tanta.fci.reoil.password.model;

import edu.tanta.fci.reoil.user.validation.PasswordConfirmed;
import edu.tanta.fci.reoil.user.validation.PasswordPolicy;

@PasswordConfirmed
public record ResetPassword(@PasswordPolicy String password, String passwordConfirmation) {
}
