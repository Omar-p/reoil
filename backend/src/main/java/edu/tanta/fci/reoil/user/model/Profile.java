package edu.tanta.fci.reoil.user.model;

public record Profile(
    String fullName,
    String email,
    String phoneNumber,
    Long points,
    Long usedPoints
) {
}
