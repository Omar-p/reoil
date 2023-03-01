package edu.tanta.fci.reoil.user.model;

import edu.tanta.fci.reoil.user.validation.PasswordConfirmed;
import edu.tanta.fci.reoil.user.validation.PasswordPolicy;
import edu.tanta.fci.reoil.user.validation.UniqueEmail;
import edu.tanta.fci.reoil.user.validation.UniqueUsername;

@PasswordConfirmed
public record RegistrationRequest(
    @UniqueUsername String username,
    @UniqueEmail String email,
    @PasswordPolicy String password,
    String passwordConfirmation,
    String phoneNumber
) {
}
