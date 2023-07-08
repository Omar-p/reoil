package edu.tanta.fci.reoil.worker.validation;

import edu.tanta.fci.reoil.user.validation.UniqueEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueWorkerEmailValidator.class)
public @interface UniqueWorkerEmail {
  String message() default "Email already exists";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}