package edu.tanta.fci.reoil.user.model;

import jakarta.validation.constraints.Email;

public record ProfileUpdate (

    String fullName,
    @Email String email,
    String phoneNumber
) {


}
