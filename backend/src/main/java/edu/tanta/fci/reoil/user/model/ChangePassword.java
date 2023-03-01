package edu.tanta.fci.reoil.user.model;

public record ChangePassword(
    String oldPassword,
    String password,
    String passwordConfirmation
) {
}
