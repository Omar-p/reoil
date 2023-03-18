package edu.tanta.fci.reoil.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomErrorController {

  @ExceptionHandler
  ResponseEntity<?> handleJPAViolations(TransactionSystemException exception){
    if (exception.getCause().getCause() instanceof ConstraintViolationException constraintViolationException) {
      var errorList = constraintViolationException.getConstraintViolations().stream()
          .map(constraintViolation -> {
            Map<String, String > errorMap = new HashMap<>();
            errorMap.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
            return errorMap;
          }).collect(Collectors.toList());

      return ResponseEntity.badRequest().body(errorList);
    }
    return ResponseEntity.badRequest().build();

  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<?> handleBindErrors(MethodArgumentNotValidException exception){

    var errorList = exception.getFieldErrors().stream()
        .map(fieldError -> {
          Map<String, String > errorMap = new HashMap<>();
          errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
          return errorMap;
        }).collect(Collectors.toList());

    return ResponseEntity.badRequest().body(errorList);
  }
}