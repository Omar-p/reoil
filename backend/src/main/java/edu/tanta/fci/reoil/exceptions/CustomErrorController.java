package edu.tanta.fci.reoil.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomErrorController {

  @ExceptionHandler
  ResponseEntity<?> handleJPAViolations(TransactionSystemException exception,
                                        HttpServletRequest request){
    if (exception.getCause().getCause() instanceof ConstraintViolationException constraintViolationException) {
      var errorList = constraintViolationException.getConstraintViolations().stream()
          .map(constraintViolation -> {
            Map<String, String > errorMap = new HashMap<>();
            errorMap.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
            return errorMap;
          }).toList();

      return new ResponseEntity<>(new ApiError(
          errorList,
          request.getContextPath(),
          HttpStatus.BAD_REQUEST.value(),
          LocalDateTime.now()
      ), HttpStatus.BAD_REQUEST);
    }
    return ResponseEntity.badRequest().build();

  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                             HttpServletRequest request) {
    var bindingErrors = ex.getBindingResult().getAllErrors()
        .stream()
        .map(err -> {
          if (err instanceof FieldError)
            return Map.of(((FieldError) err).getField(), err.getDefaultMessage());
          return Map.of(err.getObjectName(), err.getDefaultMessage());
        })
        .map(Map::entrySet)
        .flatMap(Set::stream)
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));



    return new ResponseEntity<>(new ApiError(
        bindingErrors,
        request.getContextPath(),
        HttpStatus.BAD_REQUEST.value(),
        LocalDateTime.now()
    ), HttpStatus.BAD_REQUEST);
  }


  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ApiError> handleAuthenticationException(AccessDeniedException e,
                                                                HttpServletRequest request,
                                                                HttpServletResponse response) {
    return new ResponseEntity<>(new ApiError(
        e.getMessage(),
        request.getRequestURI(),
        response.getStatus(),
        LocalDateTime.now()
    ), HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException e,
                                                  HttpServletRequest request,
                                                  HttpServletResponse response) {
    return new ResponseEntity<>(new ApiError(
        e.getMessage(),
        request.getRequestURI(),
        response.getStatus(),
        LocalDateTime.now()
    ), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(NotFoundException.class)
  ResponseEntity<?> handleNotFound(NotFoundException e,
                                   HttpServletRequest request,
                                   HttpServletResponse response){
    return new ResponseEntity<>(new ApiError(
        e.getMessage(),
        request.getRequestURI(),
        response.getStatus(),
        LocalDateTime.now()
    ), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(InsufficientPointException.class)
  ResponseEntity<?> handleNotEnoughPoint(InsufficientPointException e,
                                         HttpServletRequest request,
                                         HttpServletResponse response){
    return new ResponseEntity<>(new ApiError(
        e.getMessage(),
        request.getRequestURI(),
        response.getStatus(),
        LocalDateTime.now()
    ), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InCorrectPasswordException.class)
  ResponseEntity<?> handleInCorrectPasswordException(InsufficientPointException e,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response){
    return new ResponseEntity<>(new ApiError(
        e.getMessage(),
        request.getRequestURI(),
        response.getStatus(),
        LocalDateTime.now()
    ), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  ResponseEntity<?> handleUsernameNotFound(UsernameNotFoundException e,
                                           HttpServletRequest request,
                                           HttpServletResponse response){
    return new ResponseEntity<>(new ApiError(
        e.getMessage(),
        request.getRequestURI(),
        response.getStatus(),
        LocalDateTime.now()
    ), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleException(Exception e,
                                                  HttpServletRequest request,
                                                  HttpServletResponse response) {
    return new ResponseEntity<>(new ApiError(
        e.getMessage(),
        request.getRequestURI(),
        response.getStatus(),
        LocalDateTime.now()
    ), HttpStatus.INTERNAL_SERVER_ERROR);
  }

}