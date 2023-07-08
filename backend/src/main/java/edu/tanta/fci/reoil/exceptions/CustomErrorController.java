package edu.tanta.fci.reoil.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
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
  public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException e,
                                                                HttpServletRequest request,
                                                                HttpServletResponse response) {
    return new ResponseEntity<>(new ApiError(
        e.getMessage(),
        request.getRequestURI(),
        HttpStatus.FORBIDDEN.value(),
        LocalDateTime.now()
    ), HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(SubmitEmptyCartException.class)
  public ResponseEntity<ApiError> handleSubmitCartEmptyException(SubmitEmptyCartException e,
                                                              HttpServletRequest request,
                                                              HttpServletResponse response) {
    return new ResponseEntity<>(new ApiError(
        e.getMessage(),
        request.getRequestURI(),
        HttpStatus.UNPROCESSABLE_ENTITY.value(),
        LocalDateTime.now()
    ), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException e,
                                                  HttpServletRequest request,
                                                  HttpServletResponse response) {
    return new ResponseEntity<>(new ApiError(
        e.getMessage(),
        request.getRequestURI(),
        HttpStatus.UNAUTHORIZED
            .value(),
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
        HttpStatus.NOT_FOUND.value(),
        LocalDateTime.now()
    ), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({UnprocessableContentException.class, IllegalStateException.class})
  ResponseEntity<ApiError> handleUnprocessableContentException(Exception e,
                                   HttpServletRequest request,
                                   HttpServletResponse response){
    return new ResponseEntity<>(new ApiError(
        e.getMessage(),
        request.getRequestURI(),
        HttpStatus.UNPROCESSABLE_ENTITY.value(),
        LocalDateTime.now()
    ), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(InsufficientPointException.class)
  ResponseEntity<?> handleNotEnoughPoint(InsufficientPointException e,
                                         HttpServletRequest request,
                                         HttpServletResponse response){
    return new ResponseEntity<>(new ApiError(
        e.getMessage(),
        request.getRequestURI(),
        HttpStatus.BAD_REQUEST.value(),
        LocalDateTime.now()
    ), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InCorrectPasswordException.class)
  ResponseEntity<?> handleInCorrectPasswordException(InCorrectPasswordException e,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response){
    return new ResponseEntity<>(new ApiError(
        e.getMessage(),
        request.getRequestURI(),
        HttpStatus.BAD_REQUEST.value(),
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
        HttpStatus.NOT_FOUND.value(),
        LocalDateTime.now()
    ), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleException(Exception e,
                                                  HttpServletRequest request,
                                                  HttpServletResponse response) {
    log.info("r: {}", request);
    log.info("e: {}", e);

    return new ResponseEntity<>(new ApiError(
        e.getMessage(),
        request.getRequestURI(),
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        LocalDateTime.now()
    ), HttpStatus.INTERNAL_SERVER_ERROR);
  }

}