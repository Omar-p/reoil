package com.example.reoil.registration.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UsernameConflictException extends RuntimeException {
  public UsernameConflictException(String message) {
    super(message);
  }
}
