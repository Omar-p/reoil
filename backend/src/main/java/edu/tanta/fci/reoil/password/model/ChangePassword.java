package edu.tanta.fci.reoil.password.model;

public record ChangePassword(
    String oldPassword,
    String password,
    String passwordConfirmation
) {
}
