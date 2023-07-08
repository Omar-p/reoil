package edu.tanta.fci.reoil.worker.requests;

import edu.tanta.fci.reoil.user.validation.PasswordConfirmed;
import edu.tanta.fci.reoil.user.validation.PasswordPolicy;
import edu.tanta.fci.reoil.worker.validation.UniqueWorkerEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@PasswordConfirmed
public record WorkerRegistrationRequest(
    @NotBlank @NotNull String name,
    @NotBlank @NotNull @UniqueWorkerEmail  @Email String email,
    @PasswordPolicy String password,

    String passwordConfirmation,
    @NotBlank @NotNull String phone

) {
}
