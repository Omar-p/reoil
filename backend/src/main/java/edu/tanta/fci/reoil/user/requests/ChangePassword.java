package edu.tanta.fci.reoil.user.requests;

public record ChangePassword(
    String oldPassword,
    String newPassword,
    String newPasswordConfirmation
) {
}
