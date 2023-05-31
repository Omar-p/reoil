package edu.tanta.fci.reoil.exceptions;

public class InsufficientPointException extends RuntimeException {
  public InsufficientPointException() {
    super("in sufficient points to make a transaction");
  }
}
