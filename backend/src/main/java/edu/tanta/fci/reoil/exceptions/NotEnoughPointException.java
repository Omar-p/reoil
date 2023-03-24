package edu.tanta.fci.reoil.exceptions;

public class NotEnoughPointException extends RuntimeException {
  public NotEnoughPointException() {
    super("Not enough points");
  }
}
