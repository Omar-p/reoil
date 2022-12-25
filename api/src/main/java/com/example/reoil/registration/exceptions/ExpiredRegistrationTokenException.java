package com.example.reoil.registration.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExpiredRegistrationTokenException extends RuntimeException {
  public ExpiredRegistrationTokenException(String message) {
    super(message);
  }
}