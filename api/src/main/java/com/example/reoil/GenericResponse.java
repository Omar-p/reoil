package com.example.reoil;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record GenericResponse(String message, Object data) {
  public GenericResponse(String message) {
    this(message, null);
  }
}
