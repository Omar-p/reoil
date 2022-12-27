package com.example.reoil.registration.dtos;

import com.example.reoil.registration.validation.UniqueEmail;
import com.example.reoil.registration.validation.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record UserRegistration(
    @Pattern (regexp = "^[a-zA-Z0-9]{3,20}", message = "len between 3 and 20, only chars and nums are allowed")
    @UniqueUsername
    String username,
    @UniqueEmail
    String email,
    String password,
    String passwordRepeat,
    String phone
) {

}
