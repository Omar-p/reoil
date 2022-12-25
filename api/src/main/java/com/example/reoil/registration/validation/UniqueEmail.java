package com.example.reoil.registration.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueEmailValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Email
public @interface UniqueEmail {

  String message() default "email already exist";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};

}
