package edu.tanta.fci.reoil.user.requests;

public record RegistrationRequest(
    String username,
    String email,
    String password,
    String passwordConfirmation,
    String phoneNumber
) {
}
