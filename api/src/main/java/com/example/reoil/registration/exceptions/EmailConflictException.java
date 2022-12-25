package com.example.reoil.registration.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailConflictException extends RuntimeException {
  public EmailConflictException(String message) {
    super(message);
  }
}
