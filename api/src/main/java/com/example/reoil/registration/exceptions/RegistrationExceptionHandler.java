package com.example.reoil.registration.exceptions;

import com.example.reoil.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RegistrationExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<GenericResponse> handle(MethodArgumentNotValidException ex) {
    var errorMap = new HashMap<String, String>();
    ex.getBindingResult().getFieldErrors().forEach(err -> {
      errorMap.put(err.getField(), err.getDefaultMessage());
    });
    HttpStatus responseStatus = isConflict(errorMap) ? HttpStatus.CONFLICT : HttpStatus.BAD_REQUEST;

    return new ResponseEntity<>(new GenericResponse(null, errorMap), responseStatus);

  }

  private boolean isConflict(Map<String, String> errorsMap) {
    return errorsMap.values()
        .stream()
        .allMatch(msg -> msg.contains("exist"));
  }
}
