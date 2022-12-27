package com.example.reoil.registration.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRegistrationTokenException extends RuntimeException {
  public InvalidRegistrationTokenException(String message) {
    super(message);
  }
}
