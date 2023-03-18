package edu.tanta.fci.reoil.user.model;

public record NewAddress(
    String title,
    String address,
    String firstName,
    String lastName,

    String phoneNumber,

    boolean isMain) {
}
