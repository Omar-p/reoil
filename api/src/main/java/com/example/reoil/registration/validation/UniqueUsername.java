package com.example.reoil.registration.validation;

import jakarta.validation.Constraint;

import java.lang.annotation.*;

import jakarta.validation.Payload;
import jakarta.validation.constraints.Size;

@Documented
@Constraint(validatedBy = UniqueUsernameValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {

  String message() default "username already exist";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};

}
