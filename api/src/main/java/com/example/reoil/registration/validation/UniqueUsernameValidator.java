package com.example.reoil.registration.validation;

import com.example.reoil.user.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public final class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

  private final UserService userService;

  public UniqueUsernameValidator(UserService userService) {
    this.userService = userService;
  }


  @Override
  public boolean isValid(String username, ConstraintValidatorContext context) {
    return this.userService.findByUsername(username).isEmpty();
  }
}
