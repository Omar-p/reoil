package com.example.reoil.registration.validation;

import com.example.reoil.registration.exceptions.EmailConflictException;
import com.example.reoil.user.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public final class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

  private final UserService userService;

  public UniqueEmailValidator(UserService userService) {
    this.userService = userService;
  }


  @Override
  public boolean isValid(String email, ConstraintValidatorContext context) {
    return this.userService.findByEmail(email).isEmpty();
  }
}
