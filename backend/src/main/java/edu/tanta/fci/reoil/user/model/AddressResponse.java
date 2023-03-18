package edu.tanta.fci.reoil.user.model;

public record AddressResponse(
    Long id,
    String title,
    String address,
    String fullName,
    String phoneNumber,
    boolean isMain
) {
}
