package edu.tanta.fci.reoil.password.model;

import edu.tanta.fci.reoil.user.validation.PasswordConfirmed;

@PasswordConfirmed
public record ResetPassword(String password, String passwordConfirmation) {
}